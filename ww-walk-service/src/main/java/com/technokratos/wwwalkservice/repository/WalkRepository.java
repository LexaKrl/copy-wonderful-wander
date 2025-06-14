package com.technokratos.wwwalkservice.repository;

import com.technokratos.wwwalkservice.entity.Walk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalkRepository extends JpaRepository<Walk, UUID> {

    @Query("""
    SELECT w FROM Walk w JOIN UserWalkVisibility uwv ON w.ownerId = uwv.userId
    WHERE w.walkId = :walkId AND (
        uwv.walkVisibility = 'PUBLIC'
        OR (uwv.walkVisibility = 'FRIENDS_ONLY' AND :requesterId IN :subscribers)
        OR (uwv.walkVisibility = 'REMEMBER_ONLY' AND :requesterId MEMBER OF w.walkParticipants)
    )
    """
    )
    Optional<Walk> findById(UUID requesterId, List<UUID> subscribers, UUID walkId);

    @Query("""
    SELECT w FROM Walk w JOIN UserWalkVisibility uwv ON w.ownerId = uwv.userId
    WHERE (
        uwv.walkVisibility = 'PUBLIC'
        OR (uwv.walkVisibility = 'FRIENDS_ONLY' AND :requesterId IN :subscribers)
        OR (uwv.walkVisibility = 'REMEMBER_ONLY' AND :requesterId MEMBER OF w.walkParticipants)
    )
    """)
    Page<Walk> findAllVisible(UUID requesterId, List<UUID> subscribers, Pageable pageable);

    @Query("""
    SELECT w From Walk w JOIN UserWalkVisibility uwv ON w.ownerId = uwv.userId
    WHERE w.ownerId = :userId AND (
        uwv.walkVisibility = 'PUBLIC'
        OR (:requesterId = :userId)
        OR (uwv.walkVisibility = 'FRIENDS_ONLY' AND :requesterId IN :subscribers)
        OR (uwv.walkVisibility = 'REMEMBER_ONLY' AND :requesterId MEMBER OF w.walkParticipants)
    )
    """)
    Page<Walk> findVisibleForUser(UUID userId, UUID requesterId, List<UUID> subscribers, Pageable pageable);

    @Query("""
    SELECT w from Walk w JOIN UserWalkVisibility uwv ON w.ownerId = uwv.userId
    WHERE :requesterId IN w.walkParticipants AND (
        uwv.walkVisibility = 'PUBLIC'
        OR (uwv.walkVisibility = 'FRIENDS_ONLY' AND :requesterId IN :subscribers)
        OR (uwv.walkVisibility = 'REMEMBER_ONLY')
        OR (w.ownerId = :requesterId)
    )
    """
    )
    Page<Walk> findAllVisibleWhereUserParticipant(UUID requesterId, List<UUID> subscribers, Pageable pageable);


    @Query("""
    SELECT w FROM Walk w JOIN UserWalkVisibility uwv ON w.ownerId = uwv.userId
    WHERE w.ownerId IN :subscribers AND (
        uwv.walkVisibility = 'PUBLIC'
        OR (uwv.walkVisibility = 'FRIENDS_ONLY' AND :requesterId IN :subscribers)
        OR (uwv.walkVisibility = 'REMEMBER_ONLY' AND :requesterId MEMBER OF w.walkParticipants)
    )
    """
    )
    Page<Walk> findAllVisibleUserSubscribedOn(UUID requesterId, List<UUID> subscribers, Pageable pageable);
}
