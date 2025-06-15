package com.technokratos.wwwalkservice.service.impl;


import com.technokratos.dto.request.walk.WalkRequest;
import com.technokratos.dto.response.walk.WalkResponse;
import com.technokratos.enums.walk.WalkStatus;
import com.technokratos.wwwalkservice.config.properties.ApplicationUrlProperties;
import com.technokratos.wwwalkservice.entity.*;
import com.technokratos.wwwalkservice.exception.*;
import com.technokratos.wwwalkservice.mapper.WalkMapper;
import com.technokratos.wwwalkservice.repository.UserWalkVisibilityRepository;
import com.technokratos.wwwalkservice.repository.WalkInvitationRepository;
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
    private final WalkInvitationRepository walkInvitationRepository;
    private final ApplicationUrlProperties applicationUrlProperties; /* TODO use when send messages to notification service */

    private static final int MAX_PARTICIPANT_AMOUNT = 20;
    private static final Double SIMPLIFYING_TOLERANCE = 0.0001D;

    @Override
    public Page<WalkResponse> findAllVisible(UUID requesterId, List<UUID> subscribers, Pageable pageable) {
        return walkRepository.findAllVisible(requesterId, subscribers, pageable)
                .map(walkMapper::toResponse);
    }

    @Override
    public Page<WalkResponse> findAllVisibleForUser(UUID userId, UUID requesterId, List<UUID> subscribers, Pageable pageable) {
        return walkRepository.findVisibleForUser(userId, requesterId, subscribers, pageable)
                .map(walkMapper::toResponse);
    }

    @Override
    public Page<WalkResponse> findAllVisibleWhereUserParticipant(UUID requesterId, List<UUID> subscribers, Pageable pageable) {
        return walkRepository.findAllVisibleWhereUserParticipant(requesterId, subscribers, pageable)
                .map(walkMapper::toResponse);
    }

    @Override
    public Page<WalkResponse> findAllVisibleUserSubscribedOn(UUID requesterId, List<UUID> subscribers, Pageable pageable) {
        return walkRepository.findAllVisibleUserSubscribedOn(requesterId, subscribers, pageable)
                .map(walkMapper::toResponse);
    }

    @Override
    public WalkResponse findById(UUID requesterId, List<UUID> subscribers, UUID walkId) {
        return walkRepository.findById(requesterId, subscribers, walkId)
                .map(walkMapper::toResponse)
                .orElseThrow(() -> new WalkNotFoundException(walkId));
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
        if (!walkRequest.walkParticipants().stream() /* check if user exists in user_walk_visibility table */
                .allMatch(userWalkVisibilityRepository::existsById)) {throw new WalkSaveUserException(userId, "At least one user doesn't exist");}
        Walk savedWalk = walkRepository.save(walk);
        walkRequest.walkParticipants().forEach(p -> addParticipant(savedWalk.getWalkId(), p));
    }

    @Override
    public void updateById(UUID walkId, WalkRequest walkRequest) {
        Walk existingWalk = walkRepository.findById(walkId).orElseThrow(() -> new WalkNotFoundException(walkId));
        if (!existingWalk.getOwnerId().equals(getCurrentUserId())) {throw new WalkAccessDeniedException(walkId);}
        if (!existingWalk.getWalkStatus().equals(WalkStatus.NOT_STARTED)) {throw new WalkUpdateException(walkId, "Couldn't update walk after it was started");}
        if (walkRequest.walkParticipants().size() > MAX_PARTICIPANT_AMOUNT) {throw new WalkParticipantOverflowException(walkId);}
        if (!walkRequest.walkParticipants().stream() /* check if user exists in user_walk_visibility table */
                .allMatch(userWalkVisibilityRepository::existsById)) {throw new WalkUpdateException(walkId, "At least one user doesn't exist");}
        walkRequest.walkParticipants().forEach(p -> addParticipant(walkId, p));
        walkMapper.updateFromRequest(existingWalk, walkRequest);
        walkRepository.save(existingWalk);
    }

    @Override
    public void addParticipant(UUID walkId, UUID participantId) {
        Walk existingWalk = walkRepository.findById(walkId).orElseThrow(() -> new WalkNotFoundException(walkId));
        if (!existingWalk.getOwnerId().equals(getCurrentUserId())) {throw new WalkAccessDeniedException(walkId);}
        if (!userWalkVisibilityRepository.existsById(participantId)) {throw new WalkUpdateException(walkId);}

        WalkInvitation invitation = WalkInvitation.builder()
                .walk(existingWalk)
                .participantId(participantId)
                .token(UUID.randomUUID())
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(7))
                .accepted(false)
                .build();

        walkInvitationRepository.save(invitation);

        /* TODO send kafka message to notification service FCM like
            (https://wonderful-wander.ru:8080/api/walks/{walkId}/participant/{participantId}/accept?acceptationToken) */
        /* TODO send rabbit notification to notify user
            (https://wonderful-wander.ru:8080/api/walks/{walkId}/participant/{participantId}/accept?acceptationToken) */
    }

    @Override
    public void acceptInvite(UUID walkId, UUID participantId, UUID acceptationToken) {
        WalkInvitation invitation = walkInvitationRepository.findByToken(acceptationToken)
                .orElseThrow(() -> new WalkInvalidTokenException("Invalid invitation token"));
        UUID invitationWalkId = invitation.getWalk().getWalkId();
        if (!invitationWalkId.equals(walkId)) {throw new WalkInvalidTokenException("Token doesn't match walk ID");}
        if (LocalDateTime.now().isAfter(invitation.getExpiresAt())) {throw new WalkInvalidTokenException("Invitation expired");}
        if (invitation.isAccepted()) {return;}

        invitation.setAccepted(true);
        walkInvitationRepository.save(invitation);

        Walk existingWalk = walkRepository.findById(invitationWalkId).orElseThrow(() -> new WalkNotFoundException(walkId));
        if (userWalkVisibilityRepository.existsById(participantId)) {throw new WalkUpdateException(walkId);}
        if (existingWalk.getWalkParticipants().size() + 1 > MAX_PARTICIPANT_AMOUNT) {throw new WalkParticipantOverflowException(walkId);}
        existingWalk.getWalkParticipants().add(participantId);
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
        return walkRepository.findById(walkId).orElseThrow(() -> new WalkNotFoundException(walkId))
                .getOwnerId()
                .equals(getCurrentUserId());
    }

    @Override
    public boolean isParticipant(UUID walkId) {
        return walkRepository.findById(walkId).orElseThrow(() -> new WalkNotFoundException(walkId))
                .getWalkParticipants()
                .contains(getCurrentUserId());
    }

    /* TODO define kafka consumer(handler) */
    public void saveWalkVisibility(UserWalkVisibility userWalkVisibility) {
        userWalkVisibilityRepository.save(userWalkVisibility);
    }

    /* TODO define kafka consumer */
    public void deleteWalkVisibility(UUID userId) {
        userWalkVisibilityRepository.deleteByUserId(userId);
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
