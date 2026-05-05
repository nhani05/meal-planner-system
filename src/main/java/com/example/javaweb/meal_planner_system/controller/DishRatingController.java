package com.example.javaweb.meal_planner_system.controller;

import com.example.javaweb.meal_planner_system.dto.DishRatingDTO;
import com.example.javaweb.meal_planner_system.security.JwtUtil;
import com.example.javaweb.meal_planner_system.service.DishRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/dishes")
@CrossOrigin(origins = "*")
public class DishRatingController {

    @Autowired
    private DishRatingService dishRatingService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/{dishId}/ratings")
    public ResponseEntity<?> rateDish(@PathVariable Long dishId, @RequestBody DishRatingDTO ratingDTO, HttpServletRequest request) {
        // Lấy accountId từ token hoặc từ DTO (nếu FE gửi kèm). 
        // Trong trường hợp này FE userService gọi addFavorite(dishId, accountId)
        // Nhưng rateDish ở dishService.js chỉ truyền (dishId, ratingData)
        // Nên ta cần lấy userId từ token hoặc từ localStorage mà FE đã gửi.
        // Giả sử JwtUtil có thể extract userId.
        
        String authHeader = request.getHeader("Authorization");
        Long accountId = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            accountId = jwtUtil.extractUserId(token);
        }
        
        if (accountId == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        
        return ResponseEntity.ok(dishRatingService.rate(dishId, accountId, ratingDTO));
    }

    @GetMapping("/{dishId}/ratings")
    public ResponseEntity<?> getDishRatings(@PathVariable Long dishId) {
        return ResponseEntity.ok(dishRatingService.findByDishId(dishId));
    }
}
