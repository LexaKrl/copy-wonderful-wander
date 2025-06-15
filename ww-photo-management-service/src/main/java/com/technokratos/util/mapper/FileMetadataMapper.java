package com.technokratos.util.mapper;

import com.technokratos.dto.PhotoUploadRequest;
import com.technokratos.entity.FileMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FileMetadataMapper {
    @Mapping(target = "fileId", source = "photoUploadRequest.photoId")
    @Mapping(target = "size", source = "size")
    FileMetadata toEntity(PhotoUploadRequest photoUploadRequest,
                          String extension,
                          String fileType,
                          Double size,
                          LocalDateTime uploadDateTime);

}
