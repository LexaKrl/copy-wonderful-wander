package com.technokratos.service;

import com.mongodb.client.result.DeleteResult;
import com.technokratos.dto.response.post.LikeResponse;
import com.technokratos.dto.response.user.UserCompactResponse;
import com.technokratos.exception.ConflictServiceException;
import com.technokratos.exception.ForbiddenServiceException;
import com.technokratos.exception.PostByIdNotFoundException;
import com.technokratos.model.EmbeddedUser;
import com.technokratos.model.LikeEntity;
import com.technokratos.model.PostEntity;
import com.technokratos.repository.LikeRepository;
import com.technokratos.repository.PostRepository;
import com.technokratos.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserMapper userMapper;
    private final UserService userService;
    private final MongoTemplate mongoTemplate;
    private final PostRepository postRepository;
    private final PostService postService;


    public List<UserCompactResponse> getLikesByPostId(String viewerId, String postId) {

        String userId = postService.getUserIdByPostId(postId);

        if (!postService.checkPostPrivacy(viewerId, userId, userService.getMyPhotoVisibility(userId))) {
            throw new ForbiddenServiceException("You are not authorized to view the likes of this post");
        }

        List<UserCompactResponse> userCompactResponses = likeRepository.findByPostId(postId)
                .stream()
                .map(LikeEntity::getUser)
                .map(embeddedUser -> userMapper.toUserCompactResponse(
                        embeddedUser,
                        UUID.fromString(embeddedUser.getUserId()),
                        "test.jpg" //todo сделать получение из минио
                ))
                .toList();

        return userCompactResponses; //todo сделать правильную пагинацию
    }

    public LikeResponse createLike(String currentUserId, String postId) {

        if (!postRepository.existsById(postId)) {
            throw new PostByIdNotFoundException(postId);
        }

        if (likeExists(currentUserId, postId)) {
            throw new ConflictServiceException("The like has already been set");
        }

        EmbeddedUser user = userMapper.toEmbeddedUserEntity(userService.getUserById(currentUserId));

        LikeEntity like = LikeEntity.builder()
                .likeId(String.valueOf(UUID.randomUUID()))
                .postId(postId)
                .user(user)
                .build();

        likeRepository.save(like);

        mongoTemplate.updateFirst(
                Query.query(Criteria.where("postId").is(postId)),
                new Update().inc("likesCount", 1),
                PostEntity.class
        );

        return new LikeResponse(postService.getLikesCountByPostId(postId));
    }

    public LikeResponse deleteLike(String currentUserId, String postId) {

        Query query = new Query(
                Criteria.where("user.userId").is(currentUserId)
                        .and("postId").is(postId)
        );

        DeleteResult deleteResult = mongoTemplate.remove(query, LikeEntity.class);

        if (deleteResult.getDeletedCount() > 0) {
            mongoTemplate.updateFirst(
                    Query.query(Criteria.where("postId").is(postId)),
                    new Update().inc("likesCount", -1),
                    PostEntity.class
            );
        }

        return new LikeResponse(postService.getLikesCountByPostId(postId));
    }


    public boolean likeExists(String userId, String postId) {
        Query query = new Query(
                Criteria.where("user.userId").is(userId)
                        .and("postId").is(postId)
        );
        return mongoTemplate.exists(query, LikeEntity.class);
    }
}
