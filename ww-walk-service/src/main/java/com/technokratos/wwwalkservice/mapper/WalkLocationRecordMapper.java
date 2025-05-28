package com.technokratos.wwwalkservice.mapper;

import com.technokratos.dto.request.walk.WalkDataRequest;
import com.technokratos.dto.response.walk.WalkDataResponse;
import com.technokratos.wwwalkservice.entity.Walk;
import com.technokratos.wwwalkservice.entity.WalkPoint;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalkLocationRecordMapper {

    WalkPoint fromRequest(WalkDataRequest walkDataRequest);


    WalkDataResponse toResponse(Walk walk);
}
