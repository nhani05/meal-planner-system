package com.example.javaweb.meal_planner_system.repository;

import com.example.javaweb.meal_planner_system.entity.UserAccount;
import com.example.javaweb.meal_planner_system.entity.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for UserAccount entity
 */
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByUsername(String username);
    Optional<UserAccount> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    // UC16: User search with LIKE and status filtering
    Page<UserAccount> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(String username, String email, Pageable pageable);
    Page<UserAccount> findByStatus(UserStatus status, Pageable pageable);
    Page<UserAccount> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseAndStatus(String username, String email, UserStatus status, Pageable pageable);
}
