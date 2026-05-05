package com.example.javaweb.meal_planner_system.service.impl;

import com.example.javaweb.meal_planner_system.converter.DishCategoryConverter;
import com.example.javaweb.meal_planner_system.dto.DishCategoryDTO;
import com.example.javaweb.meal_planner_system.repository.DishCategoryRepository;
import com.example.javaweb.meal_planner_system.service.DishCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishCategoryServiceImpl implements DishCategoryService {

    @Autowired
    private DishCategoryRepository dishCategoryRepository;

    @Override
    public List<DishCategoryDTO> findAll() {
        return dishCategoryRepository.findAll().stream()
                .map(DishCategoryConverter::toDTO)
                .collect(Collectors.toList());
    }
}
