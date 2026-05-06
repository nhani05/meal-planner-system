package com.example.javaweb.meal_planner_system.service.impl;

import com.example.javaweb.meal_planner_system.converter.DishCategoryConverter;
import com.example.javaweb.meal_planner_system.dto.DishCategoryDTO;
import com.example.javaweb.meal_planner_system.entity.DishCategory;
import com.example.javaweb.meal_planner_system.exception.BadRequestException;
import com.example.javaweb.meal_planner_system.exception.ResourceNotFoundException;
import com.example.javaweb.meal_planner_system.repository.DishCategoryRepository;
import com.example.javaweb.meal_planner_system.repository.DishRepository;
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

    @Autowired
    private DishRepository dishRepository;

    @Override
    public DishCategoryDTO create(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new BadRequestException("Category name is required");
        }
        if (dishCategoryRepository.existsByName(name.trim())) {
            throw new BadRequestException("Category name already exists");
        }
        DishCategory category = new DishCategory();
        category.setName(name.trim());
        DishCategory saved = dishCategoryRepository.save(category);
        return DishCategoryConverter.toDTO(saved);
    }

    @Override
    public DishCategoryDTO update(Integer id, String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new BadRequestException("Category name is required");
        }
        DishCategory category = dishCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        if (!category.getName().equalsIgnoreCase(name.trim()) && dishCategoryRepository.existsByName(name.trim())) {
            throw new BadRequestException("Category name already exists");
        }
        category.setName(name.trim());
        DishCategory saved = dishCategoryRepository.save(category);
        return DishCategoryConverter.toDTO(saved);
    }

    @Override
    public void delete(Integer id) {
        DishCategory category = dishCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        if (dishRepository.existsByCategoryId(id)) {
            throw new BadRequestException("Cannot delete category: it is currently used by dishes");
        }
        dishCategoryRepository.delete(category);
    }
}
