package com.example.javaweb.meal_planner_system.repository;

import com.example.javaweb.meal_planner_system.entity.HealthProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for HealthProfile entity
 */
@Repository
public interface HealthProfileRepository extends JpaRepository<HealthProfile, Long> {
    Optional<HealthProfile> findByAccountId(Long accountId);
}
