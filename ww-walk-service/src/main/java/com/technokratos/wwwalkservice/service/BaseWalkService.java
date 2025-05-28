package com.technokratos.wwwalkservice.service;


import com.technokratos.dto.request.walk.WalkRequest;
import com.technokratos.dto.response.walk.WalkResponse;
import com.technokratos.wwwalkservice.entity.Walk;
import com.technokratos.wwwalkservice.exception.WalkNotFoundException;
import com.technokratos.wwwalkservice.mapper.WalkMapper;
import com.technokratos.wwwalkservice.repository.WalkRepository;
import com.technokratos.wwwalkservice.service.service_interface.WalkService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BaseWalkService implements WalkService {

    private final WalkRepository walkRepository;
    private final WalkMapper walkMapper;

    @Override
    public Page<WalkResponse> findAll(Pageable pageable) {
        return walkRepository.findAll(pageable).map(walkMapper::toResponse);
    }

    @Override
    public WalkResponse findById(UUID id) {
        return walkRepository.findById(id).map(walkMapper::toResponse).orElseThrow(() -> new WalkNotFoundException(id));
    }

    @Override
    public void deleteById(UUID id) {
        walkRepository.deleteById(id);
    }

    @Override
    public void saveWalk(WalkRequest walkRequest) {
        walkRepository.save(walkMapper.toEntity(walkRequest));
    }

    @Override
    public void updateById(UUID id, WalkRequest walkRequest) {
        Walk existingWalk = walkRepository.findById(id).orElseThrow(() -> new WalkNotFoundException(id));
        walkMapper.updateFromRequest(existingWalk, walkRequest);
        walkRepository.save(existingWalk);
    }

}
