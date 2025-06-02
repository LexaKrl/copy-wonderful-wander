package ru.itis.danyook.service;

import com.technokratos.dto.request.post.PostRequest;
import com.technokratos.dto.response.post.PostResponse;
import com.technokratos.enums.user.PhotoVisibility;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itis.danyook.exception.CategoryByIdNotFoundException;
import ru.itis.danyook.exception.ForbiddenServiceException;
import ru.itis.danyook.exception.PostByIdNotFoundException;
import ru.itis.danyook.model.*;
import ru.itis.danyook.repository.CategoryRepository;
import ru.itis.danyook.repository.PostRepository;
import ru.itis.danyook.repository.SavedPostRepository;
import ru.itis.danyook.util.mapper.PostMapper;
import ru.itis.danyook.util.mapper.UserMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final SavedPostRepository savedPostRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final PostMapper postMapper;
    private final UserMapper userMapper;


    public List<PostResponse> getRecommendedPosts(UUID currentUserId, Pageable pageable) {
        return List.of();//todo после сервиса рекомендаций
    }

    public List<PostResponse> getSavedPostsByUserId(UUID viewerId, UUID userId, Pageable pageable) {

        PhotoVisibility myPhotoVisibility = userService.getMyPhotoVisibility(userId);

        if (!checkPostPrivacy(viewerId, userId, myPhotoVisibility)) {
            throw new ForbiddenServiceException("You don`t have authority to see this post");
        }//todo проверить

        List<UUID> savedPostIds = savedPostRepository.findPostIdByUserId(userId, pageable);

        List<PostEntity> posts = postRepository.findAllById(savedPostIds);

        return postMapper.toPostResponse(posts);//todo сделать получение фото из минио по id и отдачу url
    }

    public List<PostResponse> getPostsByUserId(UUID viewerId, UUID userId, Pageable pageable) {

        PhotoVisibility savedPhotoVisibility = userService.getSavedPhotoVisibility(userId);

        if (!checkPostPrivacy(viewerId, userId, savedPhotoVisibility)) {
            throw new ForbiddenServiceException("You don`t have authority to see this post");
        }//todo проверить

        List<PostEntity> posts = postRepository.findByUser_UserId(userId, pageable);
        return postMapper.toPostResponse(posts);//todo сделать получение фото из минио по id и отдачу url
    }

    public PostResponse getPostById(UUID viewerId, UUID postId) {

        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new PostByIdNotFoundException(postId));
        UUID userId = post.getUserId();

        PhotoVisibility savedPhotoVisibility = userService.getSavedPhotoVisibility(userId);

        if (!checkPostPrivacy(viewerId, userId, savedPhotoVisibility)) {
            throw new ForbiddenServiceException("You don`t have authority to see this post");
        }//todo проверить

        return postMapper.toPostResponse(post);//todo сделать получение фото из минио по id и отдачу url
    }

    public PostResponse create(UUID currentUserId, PostRequest createPostRequest) {
        CachedUserEntity cachedUser = userService.getUserById(currentUserId);

        EmbeddedUser user = userMapper.toEmbeddedUserEntity(cachedUser);
        PostEntity post = postMapper.toPostEntity(createPostRequest);
        post.setUser(user);

        CategoryEntity category = categoryRepository.findById(createPostRequest.categoryId())
                .orElseThrow(() -> new CategoryByIdNotFoundException(post.getCategoryId()));
        post.setCategory(postMapper.toEmbeddedCategoryEntity(category));

        postRepository.save(post);

        return postMapper.toPostResponse(post);//todo сделать получение фото из минио по id и отдачу url
    }

    public PostResponse update(UUID currentUserId, UUID postId, PostRequest updatePostRequest) {
        PostEntity post = postRepository.findById(postId).orElseThrow(() -> new PostByIdNotFoundException(postId));

        if (!post.getUser().getUserId().equals(currentUserId)) {
            throw new ForbiddenServiceException("You can't edit this post");
        }

        post.setTitle(updatePostRequest.title());
        post.setImageId(updatePostRequest.imageId());
        post.setCategoryId(updatePostRequest.categoryId());


        CategoryEntity category = categoryRepository.findById(updatePostRequest.categoryId())
                .orElseThrow(() -> new CategoryByIdNotFoundException(post.getCategoryId()));
        post.setCategory(postMapper.toEmbeddedCategoryEntity(category));

        postRepository.save(post);

        return postMapper.toPostResponse(post);//todo сделать получение фото из минио по id и отдачу url
        }

    public void delete(UUID currentUserId, UUID postId) {
        postRepository.findById(postId)
                .ifPresent(post -> {
                    if (!post.getUser().getUserId().equals(currentUserId)) {
                        throw new ForbiddenServiceException("You can't delete this post");
                    }
                    postRepository.deleteById(postId);
                    savedPostRepository.deleteByPostId(postId);
                });
    }

    public void savePost(UUID currentUserId, UUID postId) {
        SavedPostEntity savedPost = new SavedPostEntity();

        savedPost.setId(UUID.randomUUID());
        savedPost.setPostId(postId);
        savedPost.setUserId(currentUserId);
        savedPost.setSavedAt(LocalDateTime.now());

        savedPostRepository.save(savedPost);
    }

    public void deleteSavedPost(UUID currentUserId, UUID postId) {
        savedPostRepository.deleteByUserIdAndPostId(currentUserId, postId);
    }

    private boolean checkPostPrivacy(UUID viewerId, UUID userId, PhotoVisibility photoVisibility) {
        return switch (photoVisibility) {
            case PhotoVisibility.PUBLIC -> true;
            case PhotoVisibility.PRIVATE -> viewerId.equals(userId);
            case PhotoVisibility.FRIENDS_ONLY -> userService.getUserFriend(userId).contains(viewerId);
        };
    }
}

