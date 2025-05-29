package com.technokratos.wwwalkservice.mapper;


import com.technokratos.dto.request.walk.WalkRequest;
import com.technokratos.dto.response.walk.WalkResponse;
import com.technokratos.wwwalkservice.entity.Walk;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WalkMapper {

    WalkResponse toResponse(Walk walk);

    @Mapping(target = "walkId", ignore = true)
    @Mapping(target = "totalSteps", ignore = true)
    @Mapping(target = "totalMeters", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "finishedAt", ignore = true)
    @Mapping(target = "walkStatus", ignore = true)
    @Mapping(target = "photos", ignore = true)
    Walk toEntity(WalkRequest walkRequest);

    @Mapping(target = "walkId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "totalSteps", ignore = true)
    @Mapping(target = "totalMeters", ignore = true)
    @Mapping(target = "finishedAt", ignore = true)
    @Mapping(target = "walkStatus", ignore = true)
    @Mapping(target = "photos", ignore = true)
    void updateFromRequest(@MappingTarget Walk existingWalk, WalkRequest walkRequest);
}
