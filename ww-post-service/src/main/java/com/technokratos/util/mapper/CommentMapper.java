package com.technokratos.util.mapper;

import com.technokratos.dto.request.post.PostRequest;
import com.technokratos.dto.response.post.CategoryResponse;
import com.technokratos.dto.response.post.PostResponse;
import com.technokratos.dto.response.post.RootCommentResponse;
import com.technokratos.dto.response.user.UserCompactResponse;
import com.technokratos.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {

    @Mapping(target = "user", source = "user")
    RootCommentResponse toRootCommentResponse(CommentEntity commentEntity, UserCompactResponse user);

}