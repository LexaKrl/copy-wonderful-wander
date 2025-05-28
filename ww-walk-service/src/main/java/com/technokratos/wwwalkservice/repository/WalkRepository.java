package com.technokratos.wwwalkservice.repository;

import com.technokratos.wwwalkservice.entity.Walk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WalkRepository extends JpaRepository<Walk, UUID> {
}
