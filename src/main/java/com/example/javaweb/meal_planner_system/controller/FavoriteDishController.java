package com.example.javaweb.meal_planner_system.controller;

import com.example.javaweb.meal_planner_system.service.FavoriteDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorites")
@CrossOrigin(origins = "*")
public class FavoriteDishController {

    @Autowired
    private FavoriteDishService favoriteDishService;

    @GetMapping("/account/{accountId}")
    public ResponseEntity<?> getFavorites(@PathVariable Long accountId) {
        return ResponseEntity.ok(favoriteDishService.findByAccountId(accountId));
    }

    @PostMapping("/account/{accountId}/{dishId}")
    public ResponseEntity<?> addFavorite(@PathVariable Long accountId, @PathVariable Long dishId) {
        favoriteDishService.addFavorite(accountId, dishId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/account/{accountId}/{dishId}")
    public ResponseEntity<?> removeFavorite(@PathVariable Long accountId, @PathVariable Long dishId) {
        favoriteDishService.removeFavorite(accountId, dishId);
        return ResponseEntity.noContent().build();
    }
}
