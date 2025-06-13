package com.technokratos.util.mapper;

import com.technokratos.dto.request.post.PostRequest;
import com.technokratos.dto.response.post.CategoryResponse;
import com.technokratos.dto.response.post.PostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import com.technokratos.model.*;

import java.util.List;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostMapper {

//    @Mapping(target = "imageUrl")
//    List<PostResponse> toPostResponse(List<PostEntity> posts);

    @Mapping(target = "imageUrl")
    PostResponse toPostResponse(PostEntity posts, String imageUrl);

    @Mapping(target = "postId", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "likesCount", ignore = true)
    @Mapping(target = "commentsCount", ignore = true)
    PostEntity toPostEntity(PostRequest postRequest);

    EmbeddedUser toEmbeddedUserEntity(CachedUserEntity cachedUserEntity);

    EmbeddedCategory toEmbeddedCategoryEntity(CategoryEntity categoryEntity);

    CategoryResponse toCategoryResponse(EmbeddedCategory embeddedCategory);


//    @Named("mapImageUrl")
//    default String mapImageUrl(String imageId) {
//        // Заглушка: формируем URL на основе imageId
//        return "https://example.com/images/"  + imageId;
//    }//todo сделать получение из минио по имени
}
