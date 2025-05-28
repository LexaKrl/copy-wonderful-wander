package com.technokratos.wwwalkservice.repository;

import com.mongodb.lang.NonNull;
import com.technokratos.wwwalkservice.entity.Walk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalkRepository extends JpaRepository<Walk, UUID> {
}
