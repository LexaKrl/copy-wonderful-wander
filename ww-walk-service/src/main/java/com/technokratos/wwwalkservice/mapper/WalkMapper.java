package com.technokratos.wwwalkservice.mapper;

import com.technokratos.dto.request.WalkRequest;
import com.technokratos.dto.response.WalkResponse;
import com.technokratos.wwwalkservice.entity.Walk;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WalkMapper {

    WalkResponse toResponse(Walk walk);

    @Mapping(target = "id", ignore = true)
    Walk toEntity(WalkRequest walkRequest);
}
