package com.example.javaweb.meal_planner_system.controller;

import com.example.javaweb.meal_planner_system.security.JwtUtil;
import com.example.javaweb.meal_planner_system.service.UserFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/feedbacks")
@CrossOrigin(origins = "*")
public class UserFeedbackController {

    @Autowired
    private UserFeedbackService userFeedbackService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> submitFeedback(@RequestBody Map<String, String> requestBody, HttpServletRequest request) {
        Long accountId = extractAccountId(request);
        if (accountId == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String content = requestBody.get("content");
        return ResponseEntity.status(201).body(userFeedbackService.submitFeedback(accountId, content));
    }

    @GetMapping
    public ResponseEntity<?> getMyFeedbacks(HttpServletRequest request) {
        Long accountId = extractAccountId(request);
        if (accountId == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        return ResponseEntity.ok(userFeedbackService.getMyFeedbacks(accountId));
    }

    private Long extractAccountId(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return jwtUtil.extractUserId(token);
        }
        return null;
    }
}
