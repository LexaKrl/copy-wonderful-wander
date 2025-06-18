package com.technokratos.mapper;

import com.technokratos.dto.request.walk.WalkDataRequest;
import com.technokratos.dto.response.walk.WalkDataResponse;
import com.technokratos.entity.WalkPoint;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalkLocationRecordMapper {

    WalkPoint fromRequest(WalkDataRequest walkDataRequest);

    WalkDataResponse toResponse(WalkPoint walkPoint);
}
