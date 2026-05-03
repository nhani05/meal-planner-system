package com.example.javaweb.meal_planner_system.controller;

// Module: Controller

import com.example.javaweb.meal_planner_system.dto.DishDTO;
import com.example.javaweb.meal_planner_system.entity.Dish;
import com.example.javaweb.meal_planner_system.entity.enums.DishSource;
import com.example.javaweb.meal_planner_system.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<?> getAllDishes() {
        List<Dish> dishes = dishService.findAll();
        return ResponseEntity.ok(dishes.stream()
                .map(dishService::convertToDTO)
                .collect(Collectors.toList()));
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
    public ResponseEntity<?> createDish(@RequestBody DishDTO dishDTO) {
        Dish dish = new Dish();
        dish.setName(dishDTO.getName());
        dish.setImageUrl(dishDTO.getImageUrl());
        dish.setSource(dishDTO.getSource() != null ? dishDTO.getSource() : DishSource.SYSTEM);
        dish.setDifficulty(dishDTO.getDifficulty());
        dish.setTotalTimeMin(dishDTO.getTotalTimeMin());

        Dish saved = dishService.save(dish);
        return ResponseEntity.ok(dishService.convertToDTO(saved));
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
