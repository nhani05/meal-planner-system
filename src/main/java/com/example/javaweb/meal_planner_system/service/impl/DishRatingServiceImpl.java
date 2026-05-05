package com.example.javaweb.meal_planner_system.service.impl;

import com.example.javaweb.meal_planner_system.converter.DishRatingConverter;
import com.example.javaweb.meal_planner_system.dto.DishRatingDTO;
import com.example.javaweb.meal_planner_system.entity.Dish;
import com.example.javaweb.meal_planner_system.entity.DishRating;
import com.example.javaweb.meal_planner_system.entity.UserAccount;
import com.example.javaweb.meal_planner_system.repository.DishRatingRepository;
import com.example.javaweb.meal_planner_system.service.DishRatingService;
import com.example.javaweb.meal_planner_system.service.DishService;
import com.example.javaweb.meal_planner_system.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishRatingServiceImpl implements DishRatingService {

    @Autowired
    private DishRatingRepository dishRatingRepository;

    @Autowired
    private DishService dishService;

    @Autowired
    private UserAccountService userAccountService;

    @Override
    public DishRatingDTO rate(Long dishId, Long accountId, DishRatingDTO ratingDTO) {
        Dish dish = dishService.findById(dishId);
        UserAccount account = userAccountService.findById(accountId);
        
        DishRating rating = dishRatingRepository.findByAccountIdAndDishId(accountId, dishId)
                .orElse(new DishRating());
        
        rating.setDish(dish);
        rating.setAccount(account);
        rating.setScore(ratingDTO.getScore());
        rating.setComment(ratingDTO.getComment());
        
        DishRating saved = dishRatingRepository.save(rating);
        return DishRatingConverter.toDTO(saved);
    }

    @Override
    public List<DishRatingDTO> findByDishId(Long dishId) {
        return dishRatingRepository.findByDishId(dishId).stream()
                .map(DishRatingConverter::toDTO)
                .collect(Collectors.toList());
    }
}
