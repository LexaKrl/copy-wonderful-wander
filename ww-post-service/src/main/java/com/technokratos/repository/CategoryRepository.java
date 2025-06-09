package com.technokratos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.technokratos.model.CategoryEntity;

public interface CategoryRepository extends MongoRepository<CategoryEntity, Long> {
}
