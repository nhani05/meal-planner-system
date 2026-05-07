package com.example.javaweb.meal_planner_system.service.impl;

import com.example.javaweb.meal_planner_system.dto.UserAccountDTO;
import com.example.javaweb.meal_planner_system.dto.RegisterDTO;
import com.example.javaweb.meal_planner_system.entity.UserAccount;
import com.example.javaweb.meal_planner_system.entity.enums.UserRole;
import com.example.javaweb.meal_planner_system.entity.enums.UserStatus;
import com.example.javaweb.meal_planner_system.entity.PasswordResetToken;
import com.example.javaweb.meal_planner_system.exception.BadRequestException;
import com.example.javaweb.meal_planner_system.repository.PasswordResetTokenRepository;
import com.example.javaweb.meal_planner_system.repository.UserAccountRepository;
import com.example.javaweb.meal_planner_system.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

// Module: Service
@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public UserAccount register(RegisterDTO registerDTO) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(registerDTO.getUsername());
        userAccount.setEmail(registerDTO.getEmail());
        userAccount.setPasswordHash(passwordEncoder.encode(registerDTO.getPassword()));
        userAccount.setRole(UserRole.USER);
        userAccount.setStatus(UserStatus.ACTIVE);
        return userAccountRepository.save(userAccount);
    }

    @Override
    public Optional<UserAccount> findByUsername(String username) {
        return userAccountRepository.findByUsername(username);
    }

    @Override
    public Optional<UserAccount> findByEmail(String email) {
        return userAccountRepository.findByEmail(email);
    }

    @Override
    public UserAccount findById(Long id) {
        if (id == null) {
            throw new com.example.javaweb.meal_planner_system.exception.BadRequestException("UserAccount id must not be null");
        }
        return userAccountRepository.findById(id)
                .orElseThrow(() -> new com.example.javaweb.meal_planner_system.exception.ResourceNotFoundException("UserAccount not found with id " + id));
    }

    @Override
    public UserAccountDTO convertToDTO(UserAccount userAccount) {
        return com.example.javaweb.meal_planner_system.converter.UserAccountConverter.toDTO(userAccount);
    }

    @Override
    public boolean usernameExists(String username) {
        return userAccountRepository.existsByUsername(username);
    }

    @Override
    public boolean emailExists(String email) {
        return userAccountRepository.existsByEmail(email);
    }

    // ===================== Phase 1: Auth Enhancements =====================

    @Override
    @Transactional
    public String generateResetToken(String email) {
        Optional<UserAccount> userOpt = userAccountRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return null; // Không leak email tồn tại
        }

        UserAccount user = userOpt.get();

        // Xóa token cũ chưa dùng của user
        passwordResetTokenRepository.deleteByAccountIdAndUsed(user.getId(), false);

        // Tạo OTP 6 chữ số
        String otp = String.format("%06d", new Random().nextInt(999999));

        PasswordResetToken token = new PasswordResetToken();
        token.setAccount(user);
        token.setToken(otp);
        token.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        token.setUsed(false);

        passwordResetTokenRepository.save(token);
        return otp;
    }

    @Override
    public void verifyOtp(String email, String otp) {
        UserAccount user = userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Invalid email or OTP"));

        PasswordResetToken token = passwordResetTokenRepository
                .findTopByAccountIdAndUsedFalseOrderByCreatedAtDesc(user.getId())
                .orElseThrow(() -> new BadRequestException("OTP not found or already used"));

        if (!token.getToken().equals(otp)) {
            throw new BadRequestException("Invalid OTP");
        }

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("OTP has expired");
        }
    }

    @Override
    @Transactional
    public void resetPassword(String tokenValue, String newPassword) {
        PasswordResetToken token = passwordResetTokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new BadRequestException("Invalid token"));

        if (token.getUsed()) {
            throw new BadRequestException("Token has already been used");
        }

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Token has expired");
        }

        UserAccount user = token.getAccount();
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userAccountRepository.save(user);

        token.setUsed(true);
        passwordResetTokenRepository.save(token);
    }

    @Override
    @Transactional
    public void changePassword(Long accountId, String oldPassword, String newPassword) {
        UserAccount user = userAccountRepository.findById(accountId)
                .orElseThrow(() -> new BadRequestException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            throw new BadRequestException("Old password is incorrect");
        }

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userAccountRepository.save(user);
    }

    @Override
    public UserAccount save(UserAccount userAccount) {
        return userAccountRepository.save(userAccount);
    }
}
