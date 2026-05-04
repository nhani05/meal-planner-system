package com.example.javaweb.meal_planner_system.controller;

import com.example.javaweb.meal_planner_system.dto.PortionDTO;
import com.example.javaweb.meal_planner_system.entity.enums.MealType;
import com.example.javaweb.meal_planner_system.service.PortionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/meal-plans")
@CrossOrigin(origins = "*")
public class PortionController {

    @Autowired
    private PortionService portionService;

    @PostMapping("/{planId}/meals/{mealType}/portions")
    public ResponseEntity<?> addPortion(
            @PathVariable Long planId,
            @PathVariable String mealType,
            @RequestBody PortionDTO portionDTO) {
        
        MealType type = MealType.fromValue(mealType.toLowerCase());
        return ResponseEntity.ok(portionService.addPortion(planId, type, portionDTO));
    }

    @PutMapping("/{planId}/meals/{mealType}/portions/{portionId}")
    public ResponseEntity<?> updatePortion(
            @PathVariable Long planId,
            @PathVariable String mealType,
            @PathVariable Long portionId,
            @RequestBody PortionDTO portionDTO) {
        
        return ResponseEntity.ok(portionService.updatePortion(portionId, portionDTO));
    }

    @DeleteMapping("/{planId}/meals/{mealType}/portions/{portionId}")
    public ResponseEntity<?> deletePortion(
            @PathVariable Long planId,
            @PathVariable String mealType,
            @PathVariable Long portionId) {
        
        portionService.deletePortion(portionId);
        return ResponseEntity.noContent().build();
    }
}
