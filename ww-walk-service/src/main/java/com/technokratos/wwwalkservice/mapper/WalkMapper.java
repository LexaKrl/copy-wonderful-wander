package com.technokratos.wwwalkservice.mapper;


import com.technokratos.dto.request.walk.WalkRequest;
import com.technokratos.dto.response.walk.WalkResponse;
import com.technokratos.wwwalkservice.entity.Walk;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WalkMapper {

    @Mapping(target = "startPointLongitude", expression = "java(getStartPointLongitude(walk.getStartPoint()))")
    @Mapping(target = "startPointLatitude", expression = "java(getStartPointLatitude(walk.getStartPoint()))")
    WalkResponse toResponse(Walk walk);

    @Mapping(target = "walkId", ignore = true)
    @Mapping(target = "ownerId", ignore = true)
    @Mapping(target = "totalSteps", ignore = true)
    @Mapping(target = "totalMeters", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "finishedAt", ignore = true)
    @Mapping(target = "walkStatus", ignore = true)
    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "route", ignore = true)
    @Mapping(target = "startPoint",
            expression = "java(createPoint(walkRequest.startPointLongitude(), walkRequest.startPointLatitude()))")
    Walk toEntity(WalkRequest walkRequest);

    @Mapping(target = "walkId", ignore = true)
    @Mapping(target = "ownerId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "totalSteps", ignore = true)
    @Mapping(target = "totalMeters", ignore = true)
    @Mapping(target = "finishedAt", ignore = true)
    @Mapping(target = "walkStatus", ignore = true)
    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "startPoint",
            expression = "java(createPoint(walkRequest.startPointLongitude(), walkRequest.startPointLatitude()))")
    void updateFromRequest(@MappingTarget Walk existingWalk, WalkRequest walkRequest);

    default Point createPoint(Double longitude, Double latitude) {
        if (longitude == null || latitude == null) {
            return null;
        }
        GeometryFactory geometryFactory = new GeometryFactory();
        return geometryFactory.createPoint(new Coordinate(longitude, latitude));
    }

    default Double getStartPointLongitude(Point point) {
        if (point == null) {
            return null;
        }
        return point.getCoordinate().getX();
    }

    default Double getStartPointLatitude(Point point) {
        if (point == null) {
            return null;
        }
        return point.getCoordinate().getY();
    }
}
