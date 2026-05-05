package com.example.javaweb.meal_planner_system.controller;

import com.example.javaweb.meal_planner_system.service.DishCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
