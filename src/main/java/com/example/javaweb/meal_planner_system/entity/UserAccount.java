package com.example.javaweb.meal_planner_system.entity;

import com.example.javaweb.meal_planner_system.entity.enums.UserRole;
import com.example.javaweb.meal_planner_system.entity.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * User Account Entity
 * Represents a user account with authentication and authorization details
 */
@Entity
@Table(name = "tblUserAccount")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Convert(converter = com.example.javaweb.meal_planner_system.entity.converter.EnumConverters.UserRoleConverter.class)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Convert(converter = com.example.javaweb.meal_planner_system.entity.converter.EnumConverters.UserStatusConverter.class)
    @Column(name = "status", nullable = false)
    private UserStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts = 0;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Account lockout methods
    public boolean isLocked() {
        return status == com.example.javaweb.meal_planner_system.entity.enums.UserStatus.LOCKED;
    }

    public void recordFailedLoginAttempt() {
        if (failedLoginAttempts == null) {
            failedLoginAttempts = 0;
        }
        failedLoginAttempts++;
        if (failedLoginAttempts >= 5) {
            status = com.example.javaweb.meal_planner_system.entity.enums.UserStatus.LOCKED;
        }
    }

    public void resetFailedLoginAttempts() {
        failedLoginAttempts = 0;
    }
}
