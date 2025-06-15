package com.technokratos.util.mapper;

import com.technokratos.dto.request.post.CommentRequest;
import com.technokratos.dto.response.comment.CommentResponse;
import com.technokratos.dto.response.comment.ReplyCommentResponse;
import com.technokratos.dto.response.comment.RootCommentResponse;
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

    @Mapping(target = "user", source = "user")
    ReplyCommentResponse toReplyCommentResponse(CommentEntity commentEntity, UserCompactResponse user);

    @Mapping(target = "commentId", ignore = true)
    @Mapping(target = "postId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "repliesCount", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    CommentEntity toCommentEntity(CommentRequest commentRequest);

    @Mapping(target = "postCommentsCount")
    CommentResponse toCommentResponse(CommentEntity commentEntity, Long postCommentsCount);


}