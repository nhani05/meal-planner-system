package com.example.javaweb.meal_planner_system.service;

import com.example.javaweb.meal_planner_system.dto.UserAccountDTO;
import com.example.javaweb.meal_planner_system.dto.RegisterDTO;
import com.example.javaweb.meal_planner_system.entity.UserAccount;

import java.util.Optional;

/**
 * Service interface for UserAccount
 */
public interface UserAccountService {
    UserAccount register(RegisterDTO registerDTO);
    Optional<UserAccount> findByUsername(String username);
    Optional<UserAccount> findByEmail(String email);
    UserAccount findById(Long id);
    UserAccountDTO convertToDTO(UserAccount userAccount);
    boolean usernameExists(String username);
    boolean emailExists(String email);

    // Phase 1: Auth enhancements
    String generateResetToken(String email);
    void verifyOtp(String email, String otp);
    void resetPassword(String token, String newPassword);
    void changePassword(Long accountId, String oldPassword, String newPassword);
}
