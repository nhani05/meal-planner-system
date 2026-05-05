package com.example.javaweb.meal_planner_system.repository;

import com.example.javaweb.meal_planner_system.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Repository for PasswordResetToken entity
 */
@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByExpiresAtBefore(LocalDateTime expirationTime);
    void deleteByAccountIdAndUsed(Long accountId, boolean used);

    Optional<PasswordResetToken> findTopByAccountIdAndUsedFalseOrderByCreatedAtDesc(Long accountId);
}
