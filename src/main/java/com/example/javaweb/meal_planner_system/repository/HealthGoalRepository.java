package com.example.javaweb.meal_planner_system.repository;

import com.example.javaweb.meal_planner_system.entity.HealthGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for HealthGoal entity
 */
@Repository
public interface HealthGoalRepository extends JpaRepository<HealthGoal, Long> {
    List<HealthGoal> findByAccountId(Long accountId);
    Optional<HealthGoal> findFirstByAccountIdOrderByUpdatedAtDesc(Long accountId);
}
