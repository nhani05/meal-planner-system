package com.example.javaweb.meal_planner_system.controller;

import com.example.javaweb.meal_planner_system.service.DishCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/dish-categories")
@CrossOrigin(origins = "*")
public class DishCategoryController {

    @Autowired
    private DishCategoryService dishCategoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(dishCategoryService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Map<String, String> body) {
        return ResponseEntity.status(201).body(dishCategoryService.create(body.get("name")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(dishCategoryService.update(id, body.get("name")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        dishCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
