package ru.itis.danyook.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.itis.danyook.model.CategoryEntity;
import ru.itis.danyook.model.PostEntity;

import java.util.UUID;

public interface CategoryRepository extends MongoRepository<CategoryEntity, Long> {
}
