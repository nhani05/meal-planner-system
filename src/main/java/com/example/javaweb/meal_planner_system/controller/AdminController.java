package com.example.javaweb.meal_planner_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.javaweb.meal_planner_system.dto.AdminDishRequestDTO;
import com.example.javaweb.meal_planner_system.entity.enums.FeedbackStatus;
import com.example.javaweb.meal_planner_system.entity.enums.UserStatus;
import com.example.javaweb.meal_planner_system.security.JwtUtil;
import com.example.javaweb.meal_planner_system.service.AdminService;
import org.springframework.data.domain.PageRequest;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/statistics")
    public ResponseEntity<?> getStats() {
        return ResponseEntity.ok(adminService.getStatistics());
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status,
            Pageable pageable) {
        UserStatus userStatus = (status != null) ? UserStatus.fromValue(status) : null;
        return ResponseEntity.ok(adminService.getAllUsers(keyword, userStatus, pageable));
    }

    @PatchMapping("/users/{id}/lock")
    public ResponseEntity<?> lockUser(@PathVariable Long id, HttpServletRequest request) {
        Long adminId = extractAdminId(request);
        adminService.updateUserStatus(id, UserStatus.LOCKED, adminId);
        return ResponseEntity.ok(java.util.Map.of("status", "locked"));
    }

    @PatchMapping("/users/{id}/unlock")
    public ResponseEntity<?> unlockUser(@PathVariable Long id, HttpServletRequest request) {
        Long adminId = extractAdminId(request);
        adminService.updateUserStatus(id, UserStatus.ACTIVE, adminId);
        return ResponseEntity.ok(java.util.Map.of("status", "active"));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, HttpServletRequest request) {
        Long adminId = extractAdminId(request);
        adminService.updateUserStatus(id, UserStatus.DELETED, adminId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/feedbacks")
    public ResponseEntity<?> getFeedbacks(
            @RequestParam(required = false) String status,
            Pageable pageable) {
        FeedbackStatus fStatus = (status != null) ? FeedbackStatus.fromValue(status) : null;
        return ResponseEntity.ok(adminService.getFeedbacks(fStatus, pageable));
    }

    @PatchMapping("/feedbacks/{id}/status")
    public ResponseEntity<?> updateFeedbackStatus(
            @PathVariable Long id,
            @RequestBody java.util.Map<String, String> body) {
        FeedbackStatus status = FeedbackStatus.fromValue(body.get("status"));
        adminService.updateFeedbackStatus(id, status);
        return ResponseEntity.ok().build();
    }

    // ===================== Phase 5: Admin Enhancements =====================

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getUserById(id));
    }

    @GetMapping("/dishes")
    public ResponseEntity<?> getDishes(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(adminService.getAllDishes(keyword, categoryId, pageable));
    }

    @PostMapping("/dishes")
    public ResponseEntity<?> createAdminDish(@RequestBody AdminDishRequestDTO request) {
        return ResponseEntity.ok(adminService.createAdminDish(request));
    }

    @PutMapping("/dishes/{id}")
    public ResponseEntity<?> updateAdminDish(@PathVariable Long id, @RequestBody AdminDishRequestDTO request) {
        return ResponseEntity.ok(adminService.updateAdminDish(id, request));
    }

    @DeleteMapping("/dishes/{id}")
    public ResponseEntity<?> deleteAdminDish(@PathVariable Long id) {
        adminService.deleteAdminDish(id);
        return ResponseEntity.noContent().build();
    }

    // UC16 NFR16-3: Admin audit logs
    @GetMapping("/audit-logs")
    public ResponseEntity<?> getAuditLogs(Pageable pageable) {
        return ResponseEntity.ok(adminService.getAuditLogs(pageable));
    }

    private Long extractAdminId(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return jwtUtil.extractUserId(token);
        }
        return null;
    }
}
