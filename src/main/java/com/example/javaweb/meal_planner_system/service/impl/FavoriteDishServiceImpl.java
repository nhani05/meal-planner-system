package com.example.javaweb.meal_planner_system.service.impl;

import com.example.javaweb.meal_planner_system.dto.DishDTO;
import com.example.javaweb.meal_planner_system.entity.Dish;
import com.example.javaweb.meal_planner_system.entity.FavoriteDish;
import com.example.javaweb.meal_planner_system.entity.UserAccount;
import com.example.javaweb.meal_planner_system.repository.FavoriteDishRepository;
import com.example.javaweb.meal_planner_system.service.DishService;
import com.example.javaweb.meal_planner_system.service.FavoriteDishService;
import com.example.javaweb.meal_planner_system.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteDishServiceImpl implements FavoriteDishService {

    @Autowired
    private FavoriteDishRepository favoriteDishRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private DishService dishService;

    @Override
    public void addFavorite(Long accountId, Long dishId) {
        UserAccount account = userAccountService.findById(accountId);
        Dish dish = dishService.findById(dishId);
        
        if (!favoriteDishRepository.existsByAccountIdAndDishId(accountId, dishId)) {
            FavoriteDish favorite = new FavoriteDish();
            favorite.setAccount(account);
            favorite.setDish(dish);
            favoriteDishRepository.save(favorite);
        }
    }

    @Override
    public void removeFavorite(Long accountId, Long dishId) {
        favoriteDishRepository.findByAccountIdAndDishId(accountId, dishId)
                .ifPresent(favoriteDishRepository::delete);
    }

    @Override
    public List<DishDTO> findByAccountId(Long accountId) {
        return favoriteDishRepository.findByAccountId(accountId).stream()
                .map(fav -> dishService.convertToDTO(fav.getDish()))
                .collect(Collectors.toList());
    }
}
