package com.example.javaweb.meal_planner_system.service.impl;

import com.example.javaweb.meal_planner_system.converter.IngredientConverter;
import com.example.javaweb.meal_planner_system.dto.IngredientDTO;
import com.example.javaweb.meal_planner_system.entity.Dish;
import com.example.javaweb.meal_planner_system.entity.Ingredient;
import com.example.javaweb.meal_planner_system.exception.ResourceNotFoundException;
import com.example.javaweb.meal_planner_system.repository.IngredientRepository;
import com.example.javaweb.meal_planner_system.service.DishService;
import com.example.javaweb.meal_planner_system.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private DishService dishService;

    @Override
    public IngredientDTO save(IngredientDTO dto) {
        Dish dish = dishService.findById(dto.getDishId());
        Ingredient ingredient = (dto.getId() != null) 
                ? ingredientRepository.findById(dto.getId()).orElse(new Ingredient())
                : new Ingredient();
        
        ingredient.setDish(dish);
        ingredient.setName(dto.getName());
        ingredient.setQuantityG(dto.getQuantityG());
        ingredient.setUnit(dto.getUnit());
        
        Ingredient saved = ingredientRepository.save(ingredient);
        return IngredientConverter.toDTO(saved);
    }

    @Override
    public IngredientDTO findById(Long id) {
        return ingredientRepository.findById(id)
                .map(IngredientConverter::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with id " + id));
    }

    @Override
    public List<IngredientDTO> findByDishId(Long dishId) {
        return ingredientRepository.findByDishId(dishId).stream()
                .map(IngredientConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!ingredientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ingredient not found with id " + id);
        }
        ingredientRepository.deleteById(id);
    }

    @Override
    public Page<IngredientDTO> findAll(Pageable pageable) {
        return ingredientRepository.findAll(pageable)
                .map(IngredientConverter::toDTO);
    }

    @Override
    public Page<IngredientDTO> searchByName(String name, Pageable pageable) {
        return ingredientRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(IngredientConverter::toDTO);
    }
}
