package com.technokratos.wwwalkservice.controller;

import com.technokratos.api.WalkApi;
import com.technokratos.dto.request.walk.WalkDataRequest;
import com.technokratos.dto.request.walk.WalkRequest;
import com.technokratos.dto.response.walk.WalkResponse;
import com.technokratos.wwwalkservice.mapper.WalkMapper;
import com.technokratos.wwwalkservice.service.service_interface.RecordLocationService;
import com.technokratos.wwwalkservice.service.service_interface.WalkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class WalkController implements WalkApi {

    private final WalkService walkService;
    private final RecordLocationService recordLocationService;
    private final WalkMapper walkMapper;

    @Override
    public ResponseEntity<Page<WalkResponse>> getWalks(Pageable pageable) {
        return ResponseEntity.ok(walkService.findAll(pageable));
    }

    @Override
    public void createWalk(WalkRequest walkRequest) {
        walkService.saveWalk(walkRequest);
    }

    @Override
    public ResponseEntity<WalkResponse> getWalk(UUID walkId) {
        return ResponseEntity.ok(walkMapper.toResponse(walkService.findById(walkId)));
    }

    @Override
    public void deleteWalk(UUID walkId) {
        walkService.deleteById(walkId);
    }

    @Override
    public void updateWalk(WalkRequest walkRequest, UUID walkId) {
        walkService.updateById(walkId, walkRequest);
    }

    @Override
    public void recordData(String walkId, List<WalkDataRequest> walkDataRequests) {

    }
}
