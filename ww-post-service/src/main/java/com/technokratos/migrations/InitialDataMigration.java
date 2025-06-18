package com.technokratos.migrations;

import com.mongodb.DuplicateKeyException;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ChangeUnit(id = "category-initial-data", order = "001", author = "ww")
public class InitialDataMigration {

    @Execution
    public void migrate(MongoTemplate mongoTemplate) {
        List<Document> categories = Arrays.asList(
                new Document("_id", 1L).append("name", "Путешествия"),
                new Document("_id", 2L).append("name", "Технологии"),
                new Document("_id", 3L).append("name", "Еда")
        );

        mongoTemplate.getCollection("category").insertMany(categories);
    }

    @RollbackExecution
    public void rollback(MongoTemplate mongoTemplate) {
        mongoTemplate.remove(
                Query.query(Criteria.where("_id").in(1L, 2L, 3L)),
                "category"
        );
    }

}