package com.example.javaweb.meal_planner_system.service.impl;

import com.example.javaweb.meal_planner_system.dto.DishDTO;
import com.example.javaweb.meal_planner_system.entity.Dish;
import com.example.javaweb.meal_planner_system.entity.enums.DishSource;
import com.example.javaweb.meal_planner_system.exception.BadRequestException;
import com.example.javaweb.meal_planner_system.exception.ResourceNotFoundException;
import com.example.javaweb.meal_planner_system.repository.DishRepository;
import com.example.javaweb.meal_planner_system.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of DishService with basic validation and exception handling.
 */
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishRepository dishRepository;

    @Override
    public Dish save(Dish dish) {
        if (dish == null) {
            throw new BadRequestException("Dish must not be null");
        }
        if (dish.getName() == null || dish.getName().trim().isEmpty()) {
            throw new BadRequestException("Dish name is required");
        }
        return dishRepository.save(dish);
    }

    @Override
    public Dish findById(Long id) {
        if (id == null) {
            throw new BadRequestException("Dish id must not be null");
        }
        return dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dish not found with id " + id));
    }

    @Override
    public List<Dish> findBySource(DishSource source) {
        return dishRepository.findBySource(source);
    }

    @Override
    public List<Dish> findByAccountId(Long accountId) {
        return dishRepository.findByAccountId(accountId);
    }

    @Override
    public List<Dish> findAll() {
        return dishRepository.findAll();
    }

    @Override
    public DishDTO convertToDTO(Dish dish) {
        return com.example.javaweb.meal_planner_system.converter.DishConverter.toDTO(dish);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new BadRequestException("Dish id must not be null");
        }
        if (!dishRepository.existsById(id)) {
            throw new ResourceNotFoundException("Dish not found with id " + id);
        }
        dishRepository.deleteById(id);
    }
}
