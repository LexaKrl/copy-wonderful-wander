package com.technokratos.wwwalkservice.service;

import com.technokratos.dto.request.WalkRequest;
import com.technokratos.dto.response.WalkResponse;
import com.technokratos.wwwalkservice.entity.Walk;
import com.technokratos.wwwalkservice.mapper.WalkMapper;
import com.technokratos.wwwalkservice.repository.WalkRepository;
import com.technokratos.wwwalkservice.service.service_interface.WalkService;
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
    public Walk findById(UUID id) {
        return walkRepository.findById(id).orElse(null);
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
        Walk updatedWalk = walkMapper.toEntity(walkRequest);

        if (walkRequest.userId() != null) {
            walkRepository.updateUserIdById(
                    id,
                    updatedWalk.getUserId().toString()
            );
        } else if (walkRequest.name() != null) {
            walkRepository.updateNameById(
                    id,
                    updatedWalk.getName()
            );
        } else if (walkRequest.description() != null) {
            walkRepository.updateDescriptionById(
                    id,
                    updatedWalk.getDescription()
            );
        }
    }

}
