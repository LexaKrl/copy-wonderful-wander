package com.technokratos.wwwalkservice.service.impl;


import com.technokratos.dto.request.walk.WalkRequest;
import com.technokratos.dto.response.walk.WalkResponse;
import com.technokratos.enums.walk.WalkStatus;
import com.technokratos.wwwalkservice.entity.UserWalkVisibility;
import com.technokratos.wwwalkservice.entity.Walk;
import com.technokratos.wwwalkservice.entity.WalkLocationData;
import com.technokratos.wwwalkservice.entity.WalkPoint;
import com.technokratos.wwwalkservice.exception.*;
import com.technokratos.wwwalkservice.mapper.WalkMapper;
import com.technokratos.wwwalkservice.repository.UserWalkVisibilityRepository;
import com.technokratos.wwwalkservice.repository.WalkRepository;
import com.technokratos.wwwalkservice.service.WalkService;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.simplify.DouglasPeuckerSimplifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BaseWalkService implements WalkService {

    private final WalkRepository walkRepository;
    private final WalkMapper walkMapper;
    private final UserWalkVisibilityRepository userWalkVisibilityRepository;

    private static final int MAX_PARTICIPANT_AMOUNT = 20;
    private static final Double SIMPLIFYING_TOLERANCE = 0.0001D;

    @Override
    public Page<WalkResponse> findAll(Pageable pageable) {
        return walkRepository.findAll(pageable).map(walkMapper::toResponse);
    }

    @Override
    public Page<WalkResponse> findAllForUser(Pageable pageable) {
        return null;  /* TODO define implementation */
    }

    @Override
    public Page<WalkResponse> findAllWhereUserParticipant(Pageable pageable) {
        return null;  /* TODO define implementation */
    }

    @Override
    public Page<WalkResponse> findAllUserSubscribedOn(Pageable pageable) {
        return null;  /* TODO define implementation */
    }

    @Override
    public WalkResponse findById(UUID id) {
        /* TODO check visibility */
        return walkRepository.findById(id).map(walkMapper::toResponse).orElseThrow(() -> new WalkNotFoundException(id));
    }

    @Override
    public void deleteById(UUID id) {
        Walk existingWalk = walkRepository.findById(id).orElseThrow(() -> new WalkNotFoundException(id));
        if (!existingWalk.getOwnerId().equals(getCurrentUserId())) {throw new WalkAccessDeniedException(id);}
        if (existingWalk.getWalkStatus().equals(WalkStatus.STARTED)) {throw new WalkDeletionException(id);}
        walkRepository.deleteById(id);
    }

    @Override
    public void saveWalk(WalkRequest walkRequest) {
        Walk walk = walkMapper.toEntity(walkRequest);
        UUID userId = getCurrentUserId();
        walk.setOwnerId(userId);
        if (walkRequest.walkParticipants().size() > MAX_PARTICIPANT_AMOUNT) {throw new WalkParticipantOverflowException("too much users in the walk");}
        if (walkRequest.walkParticipants().stream() /* check if user exists in user_walk_visibility table */
                .allMatch(userWalkVisibilityRepository::existsById)) {throw new WalkSaveUserException(userId, "At least one user doesn't exist");}
        walkRepository.save(walk);
    }

    @Override
    public void updateById(UUID walkId, WalkRequest walkRequest) {
        Walk existingWalk = walkRepository.findById(walkId).orElseThrow(() -> new WalkNotFoundException(walkId));
        if (!existingWalk.getOwnerId().equals(getCurrentUserId())) {throw new WalkAccessDeniedException(walkId);}
        if (!existingWalk.getWalkStatus().equals(WalkStatus.STARTED)) {throw new WalkUpdateException(walkId, "Walk already started");}
        if (walkRequest.walkParticipants().size() > MAX_PARTICIPANT_AMOUNT) {throw new WalkParticipantOverflowException(walkId);}
        if (walkRequest.walkParticipants().stream() /* check if user exists in user_walk_visibility table */
                .allMatch(userWalkVisibilityRepository::existsById)) {throw new WalkUpdateException(walkId, "At least one user doesn't exist");}
        walkMapper.updateFromRequest(existingWalk, walkRequest);
        walkRepository.save(existingWalk);
    }

    @Override
    public void addParticipant(UUID walkId, UUID participantId) {
        Walk existingWalk = walkRepository.findById(walkId).orElseThrow(() -> new WalkNotFoundException(walkId));
        if (!existingWalk.getOwnerId().equals(getCurrentUserId())) {throw new WalkAccessDeniedException(walkId);}
        if (userWalkVisibilityRepository.existsById(participantId)) {throw new WalkUpdateException(walkId);}
        if (existingWalk.getWalkParticipants().size() + 1 > MAX_PARTICIPANT_AMOUNT) {throw new WalkParticipantOverflowException(walkId);}
        existingWalk.getWalkParticipants().add(participantId);
        /* TODO send kafka message to notification service FCM */
        walkRepository.save(existingWalk);
    }

    @Override
    public void removeParticipant(UUID walkId, UUID participantId) {
        Walk existingWalk = walkRepository.findById(walkId).orElseThrow(() -> new WalkNotFoundException(walkId));
        if (!existingWalk.getOwnerId().equals(getCurrentUserId())) {throw new WalkAccessDeniedException(walkId);}
        existingWalk.getWalkParticipants().remove(participantId);
        walkRepository.save(existingWalk);
    }

    @Override
    public boolean isOwner(UUID walkId) {
        return findById(walkId).ownerId().equals(getCurrentUserId());
    }

    @Override
    public boolean isParticipant(UUID walkId) {
        return findById(walkId).walkParticipants().contains(getCurrentUserId());
    }

    public void saveWalkVisibility(UserWalkVisibility userWalkVisibility) {
        userWalkVisibilityRepository.save(userWalkVisibility);
    }

    public void finishWalk(UUID walkId, WalkLocationData walkLocationData) {
        Walk existingWalk = walkRepository.findById(walkId).orElseThrow(() -> new WalkNotFoundException(walkId));
        if (!existingWalk.getOwnerId().equals(getCurrentUserId())) {throw new WalkAccessDeniedException(walkId);}

        List<WalkPoint> points = walkLocationData.getPoints();

        existingWalk.setRoute(simplifyAndSaveLineString(points));
        existingWalk.setTotalMeters(points.stream().mapToInt(WalkPoint::getMeters).sum());
        existingWalk.setTotalSteps(points.stream().mapToInt(WalkPoint::getSteps).sum());
        existingWalk.setFinishedAt(LocalDateTime.now());
        existingWalk.setWalkStatus(WalkStatus.FINISHED);

        walkRepository.save(existingWalk);

        /* TODO send notification walk finished to subscribers */
    }

    private LineString simplifyAndSaveLineString(List<WalkPoint> points) {
        GeometryFactory geometryFactory = new GeometryFactory();

        Coordinate[] coordinates = points.stream()
                .map(p -> new Coordinate(p.getLongitude(), p.getLatitude()))
                .toArray(Coordinate[]::new);

        LineString originalLineString = geometryFactory.createLineString(coordinates);

        return (LineString) DouglasPeuckerSimplifier.simplify(originalLineString, SIMPLIFYING_TOLERANCE);
    }

    /*
     *   TODO add getCurrentUserId() from security starter
     * */

    private UUID getCurrentUserId() {
        return UUID.randomUUID();
    }
}
