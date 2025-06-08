package ru.itis.danyook.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.itis.danyook.model.PostEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends MongoRepository<PostEntity, UUID> {
    List<PostEntity> findByUser_UserId(UUID userId, Pageable pageable);//todo возможно поменять на просто поиск по юзер ид
}
