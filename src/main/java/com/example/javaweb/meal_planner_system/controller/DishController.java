package com.example.javaweb.meal_planner_system.controller;

// Module: Controller

import com.example.javaweb.meal_planner_system.dto.AdminDishRequestDTO;
import com.example.javaweb.meal_planner_system.dto.DishDTO;
import com.example.javaweb.meal_planner_system.entity.Dish;
import com.example.javaweb.meal_planner_system.entity.enums.DishSource;
import com.example.javaweb.meal_planner_system.security.JwtUtil;
import com.example.javaweb.meal_planner_system.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for dish management
 */
@RestController
@RequestMapping("/dishes")
@CrossOrigin(origins = "*")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<?> getAllDishes(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String minCal,
            @RequestParam(required = false) String maxCal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(dishService.searchDishes(keyword, categoryId, minCal, maxCal, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDishById(@PathVariable Long id) {
        Dish dish = dishService.findById(id); // will throw if not found
        return ResponseEntity.ok(dishService.convertToDTO(dish));
    }

    @GetMapping("/system")
    public ResponseEntity<?> getSystemDishes() {
        List<Dish> dishes = dishService.findBySource(DishSource.SYSTEM);
        return ResponseEntity.ok(dishes.stream()
                .map(dishService::convertToDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<?> getUserDishes(@PathVariable Long accountId) {
        List<Dish> dishes = dishService.findByAccountId(accountId);
        return ResponseEntity.ok(dishes.stream()
                .map(dishService::convertToDTO)
                .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<?> createDish(@RequestBody AdminDishRequestDTO request, HttpServletRequest httpRequest) {
        Long accountId = extractAccountId(httpRequest);
        if (accountId == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return ResponseEntity.ok(dishService.createCustomDish(accountId, request));
    }

    private Long extractAccountId(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return jwtUtil.extractUserId(token);
        }
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDish(@PathVariable Long id, @RequestBody DishDTO dishDTO) {
        Dish dish = dishService.findById(id); // will throw if not found

        dish.setName(dishDTO.getName());
        dish.setImageUrl(dishDTO.getImageUrl());
        dish.setDifficulty(dishDTO.getDifficulty());
        dish.setTotalTimeMin(dishDTO.getTotalTimeMin());

        Dish updated = dishService.save(dish);
        return ResponseEntity.ok(dishService.convertToDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDish(@PathVariable Long id) {
        dishService.findById(id); // will throw if not found
        dishService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
