package com.example.javaweb.meal_planner_system.service.impl;

import com.example.javaweb.meal_planner_system.dto.DishCategoryDTO;
import com.example.javaweb.meal_planner_system.entity.DishCategory;
import com.example.javaweb.meal_planner_system.exception.BadRequestException;
import com.example.javaweb.meal_planner_system.exception.ResourceNotFoundException;
import com.example.javaweb.meal_planner_system.repository.DishCategoryRepository;
import com.example.javaweb.meal_planner_system.repository.DishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DishCategoryServiceImplTest {

    private DishCategoryRepository dishCategoryRepository;
    private DishRepository dishRepository;
    private DishCategoryServiceImpl service;

    @BeforeEach
    void setUp() throws Exception {
        dishCategoryRepository = mock(DishCategoryRepository.class);
        dishRepository = mock(DishRepository.class);
        service = new DishCategoryServiceImpl();
        setField(service, "dishCategoryRepository", dishCategoryRepository);
        setField(service, "dishRepository", dishRepository);
    }

    @Test
    void findAllReturnsMappedCategories() {
        DishCategory category = category(1, "Vietnamese");
        when(dishCategoryRepository.findAll()).thenReturn(List.of(category));

        List<DishCategoryDTO> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("Vietnamese", result.get(0).getName());
    }

    @Test
    void createTrimsNameAndSavesCategory() {
        when(dishCategoryRepository.existsByName("Asian")).thenReturn(false);
        when(dishCategoryRepository.save(any(DishCategory.class))).thenAnswer(invocation -> {
            DishCategory saved = invocation.getArgument(0);
            saved.setId(2);
            return saved;
        });

        DishCategoryDTO result = service.create("  Asian  ");

        assertEquals(2, result.getId());
        assertEquals("Asian", result.getName());
        verify(dishCategoryRepository).existsByName("Asian");
    }

    @Test
    void createRejectsBlankOrDuplicateName() {
        assertThrows(BadRequestException.class, () -> service.create(" "));

        when(dishCategoryRepository.existsByName("Asian")).thenReturn(true);

        assertThrows(BadRequestException.class, () -> service.create("Asian"));
        verify(dishCategoryRepository, never()).save(category(1, "unused"));
    }

    @Test
    void updateChangesNameWhenCategoryExists() {
        DishCategory category = category(1, "Old");
        when(dishCategoryRepository.findById(1)).thenReturn(Optional.of(category));
        when(dishCategoryRepository.existsByName("New")).thenReturn(false);
        when(dishCategoryRepository.save(category)).thenReturn(category);

        DishCategoryDTO result = service.update(1, " New ");

        assertEquals(1, result.getId());
        assertEquals("New", result.getName());
        verify(dishCategoryRepository).save(category);
    }

    @Test
    void updateRejectsMissingBlankOrDuplicateCategory() {
        assertThrows(BadRequestException.class, () -> service.update(1, ""));

        when(dishCategoryRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.update(1, "New"));

        DishCategory category = category(1, "Old");
        when(dishCategoryRepository.findById(1)).thenReturn(Optional.of(category));
        when(dishCategoryRepository.existsByName("Existing")).thenReturn(true);
        assertThrows(BadRequestException.class, () -> service.update(1, "Existing"));
    }

    @Test
    void deleteRemovesUnusedCategory() {
        DishCategory category = category(1, "Unused");
        when(dishCategoryRepository.findById(1)).thenReturn(Optional.of(category));
        when(dishRepository.existsByCategoryId(1)).thenReturn(false);

        service.delete(1);

        verify(dishCategoryRepository).delete(category);
    }

    @Test
    void deleteRejectsMissingOrUsedCategory() {
        when(dishCategoryRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.delete(1));

        DishCategory category = category(2, "Used");
        when(dishCategoryRepository.findById(2)).thenReturn(Optional.of(category));
        when(dishRepository.existsByCategoryId(2)).thenReturn(true);
        assertThrows(BadRequestException.class, () -> service.delete(2));
    }

    private static DishCategory category(Integer id, String name) {
        DishCategory category = new DishCategory();
        category.setId(id);
        category.setName(name);
        return category;
    }

    private static void setField(Object target, String name, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }
}
