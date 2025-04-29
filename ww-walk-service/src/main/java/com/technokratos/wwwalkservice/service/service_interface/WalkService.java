package com.technokratos.wwwalkservice.service.service_interface;

import com.technokratos.dto.request.WalkRequest;
import com.technokratos.dto.response.WalkResponse;
import com.technokratos.wwwalkservice.entity.Walk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface WalkService {

    Page<WalkResponse> findAll(Pageable pageable);

    Walk findById(UUID id);

    void deleteById(UUID id);

    void saveWalk(WalkRequest walkRequest);

    void updateById(UUID id, WalkRequest walkRequest);
}
