package com.example.javaweb.meal_planner_system.controller;

// Module: Controller

import com.example.javaweb.meal_planner_system.dto.*;
import com.example.javaweb.meal_planner_system.entity.UserAccount;
import com.example.javaweb.meal_planner_system.service.UserAccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for user authentication and account management
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Autowired
    private com.example.javaweb.meal_planner_system.security.JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        if (registerDTO.getPassword() == null || !registerDTO.getPassword().equals(registerDTO.getPasswordConfirm())) {
            throw new com.example.javaweb.meal_planner_system.exception.BadRequestException("Passwords do not match");
        }

        if (userAccountService.usernameExists(registerDTO.getUsername())) {
            throw new com.example.javaweb.meal_planner_system.exception.BadRequestException("Username already exists");
        }

        if (userAccountService.emailExists(registerDTO.getEmail())) {
            throw new com.example.javaweb.meal_planner_system.exception.BadRequestException("Email already exists");
        }

        UserAccount userAccount = userAccountService.register(registerDTO);
        return ResponseEntity.ok(userAccountService.convertToDTO(userAccount));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        var user = userAccountService.findByUsername(loginDTO.getUsername());
        if (user.isPresent()) {
            var u = user.get();

            // Check if account is locked
            if (u.isLocked()) {
                java.util.Map<String, Object> errorBody = new java.util.HashMap<>();
                errorBody.put("message", "Account has been locked due to too many failed login attempts.");
                errorBody.put("locked", true);
                return ResponseEntity.status(423).body(errorBody); // 423 Locked
            }

            if (passwordEncoder.matches(loginDTO.getPassword(), u.getPasswordHash())) {
                // Reset failed attempts on successful login
                u.resetFailedLoginAttempts();
                userAccountService.save(u);

                String token = jwtUtil.generateToken(u);
                var dto = userAccountService.convertToDTO(u);
                java.util.Map<String, Object> body = new java.util.HashMap<>();
                body.put("token", token);
                body.put("user", dto);
                return ResponseEntity.ok(body);
            }

            // Record failed login attempt
            u.recordFailedLoginAttempt();
            userAccountService.save(u);

            // Check if account just got locked
            if (u.isLocked()) {
                java.util.Map<String, Object> errorBody = new java.util.HashMap<>();
                errorBody.put("message", "Account has been locked due to too many failed login attempts.");
                errorBody.put("locked", true);
                return ResponseEntity.status(423).body(errorBody);
            }

            int remainingAttempts = 5 - u.getFailedLoginAttempts();
            throw new com.example.javaweb.meal_planner_system.exception.BadRequestException(
                "Invalid username or password. " + remainingAttempts + " attempts remaining.");
        }
        throw new com.example.javaweb.meal_planner_system.exception.BadRequestException("Invalid username or password");
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        UserAccount user = userAccountService.findById(id); // will throw if not found
        return ResponseEntity.ok(userAccountService.convertToDTO(user));
    }

    // ===================== Phase 1: Auth Enhancements =====================

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Logout is handled client-side (token removal from store)
        return ResponseEntity.ok().build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordDTO dto) {
        String otp = userAccountService.generateResetToken(dto.getEmail());
        if (otp != null) {
            // In production, send OTP via email. For dev, we log it.
            System.out.println("[DEV] OTP for " + dto.getEmail() + ": " + otp);
            java.util.Map<String, String> body = new java.util.HashMap<>();
            body.put("message", "If the email exists, an OTP has been sent");
            body.put("devOtp", otp); // Remove this in production!
            return ResponseEntity.ok(body);
        }
        return ResponseEntity.ok(java.util.Map.of("message", "If the email exists, an OTP has been sent"));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpDTO dto) {
        userAccountService.verifyOtp(dto.getEmail(), dto.getOtp());
        return ResponseEntity.ok(java.util.Map.of("message", "OTP verified successfully"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDTO dto) {
        userAccountService.resetPassword(dto.getToken(), dto.getNewPassword());
        return ResponseEntity.ok(java.util.Map.of("message", "Password reset successfully"));
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordDTO dto,
            HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        Long accountId = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            accountId = jwtUtil.extractUserId(token);
        }
        if (accountId == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        userAccountService.changePassword(accountId, dto.getOldPassword(), dto.getNewPassword());
        return ResponseEntity.ok(java.util.Map.of("message", "Password changed successfully"));
    }
}
