package com.example.javaweb.meal_planner_system.controller;

import com.example.javaweb.meal_planner_system.security.JwtUtil;
import com.example.javaweb.meal_planner_system.service.MealPlanTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

// Module: Controller
/**
 * Controller for meal plan template management
 */
@RestController
@RequestMapping("/meal-plan-templates")
@CrossOrigin(origins = "*")
public class MealPlanTemplateController {

    @Autowired
    private MealPlanTemplateService mealPlanTemplateService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<?> getTemplates(@RequestParam Long accountId) {
        return ResponseEntity.ok(mealPlanTemplateService.findByAccountId(accountId));
    }

    @PostMapping
    public ResponseEntity<?> createTemplate(@RequestBody Map<String, Object> body, HttpServletRequest httpRequest) {
        Long accountId = extractAccountId(httpRequest);
        if (accountId == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        Long sourcePlanId = body.get("sourcePlanId") instanceof Number ? ((Number) body.get("sourcePlanId")).longValue() : null;
        String templateName = body.get("templateName") != null ? body.get("templateName").toString() : null;
        return ResponseEntity.status(201).body(mealPlanTemplateService.saveTemplate(accountId, sourcePlanId, templateName));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTemplate(@PathVariable Long id, @RequestBody Map<String, Object> body, HttpServletRequest httpRequest) {
        Long accountId = extractAccountId(httpRequest);
        if (accountId == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        String templateName = body.get("templateName") != null ? body.get("templateName").toString() : null;
        return ResponseEntity.ok(mealPlanTemplateService.updateTemplate(id, accountId, templateName));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTemplate(@PathVariable Long id, HttpServletRequest httpRequest) {
        Long accountId = extractAccountId(httpRequest);
        if (accountId == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        mealPlanTemplateService.deleteTemplate(id, accountId);
        return ResponseEntity.noContent().build();
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
