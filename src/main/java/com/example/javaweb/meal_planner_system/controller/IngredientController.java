package com.example.javaweb.meal_planner_system.controller;

import com.example.javaweb.meal_planner_system.dto.IngredientDTO;
import com.example.javaweb.meal_planner_system.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredients")
@CrossOrigin(origins = "*")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @PostMapping
    public ResponseEntity<?> createIngredient(@RequestBody IngredientDTO ingredientDTO) {
        return ResponseEntity.ok(ingredientService.save(ingredientDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateIngredient(@PathVariable Long id, @RequestBody IngredientDTO ingredientDTO) {
        ingredientDTO.setId(id);
        return ResponseEntity.ok(ingredientService.save(ingredientDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIngredientById(@PathVariable Long id) {
        return ResponseEntity.ok(ingredientService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIngredient(@PathVariable Long id) {
        ingredientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
