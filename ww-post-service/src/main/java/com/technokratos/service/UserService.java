package com.technokratos.service;

import com.technokratos.enums.user.PhotoVisibility;
import com.technokratos.exception.PostByIdNotFoundException;
import com.technokratos.model.PostEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.technokratos.model.CachedUserEntity;
import com.technokratos.repository.CachedUserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final CachedUserRepository cachedUserRepository;
    private final MongoTemplate mongoTemplate;

    public CachedUserEntity getUserById(String userId) {
//        return cachedUserRepository.findById(userId)
//                .orElseGet(() -> uploadUser(userId));

        return cachedUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not found"));
    }

    public PhotoVisibility getMyPhotoVisibility(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        query.fields().include("myPhotoVisibility");

        CachedUserEntity user = mongoTemplate.findOne(query, CachedUserEntity.class);

        log.info("getMyPhotoVisibility data: {}", user);

        if (user == null) {
            user = uploadUser(userId);
        }

        return user.getMyPhotoVisibility();
    }

    public PhotoVisibility getSavedPhotoVisibility(String userId) {
//        return getUserById(userId).getSavedPhotoVisibility();

        Query query = new Query(Criteria.where("userId").is(userId));
        query.fields().include("savedPhotoVisibility");

        CachedUserEntity user = mongoTemplate.findOne(query, CachedUserEntity.class);

        log.info("getSavedPhotoVisibility data: {}", user);

        if (user == null) {
            user = uploadUser(userId);
        }

        return user.getSavedPhotoVisibility();
    }

    public Set<String> getUserFriend(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        query.fields().include("friends");

        CachedUserEntity user = mongoTemplate.findOne(query, CachedUserEntity.class);

        log.info("get user friends data: {}", user);

        if (user == null) {
            user = uploadUser(userId);
        }

        return user.getFriends();
    }

    private CachedUserEntity uploadUser(String userId) {
        return cachedUserRepository.save(new CachedUserEntity(
                userId,
                "danyo_ok",
                "123.jpg",
                PhotoVisibility.PUBLIC,
                PhotoVisibility.PUBLIC,
                new HashSet<>())); //todo сделать загрузку через фейн клиент
    }
}
