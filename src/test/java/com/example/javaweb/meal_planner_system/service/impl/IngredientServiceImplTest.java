package com.example.javaweb.meal_planner_system.service.impl;

import com.example.javaweb.meal_planner_system.dto.IngredientDTO;
import com.example.javaweb.meal_planner_system.entity.Dish;
import com.example.javaweb.meal_planner_system.entity.Ingredient;
import com.example.javaweb.meal_planner_system.exception.ResourceNotFoundException;
import com.example.javaweb.meal_planner_system.repository.IngredientRepository;
import com.example.javaweb.meal_planner_system.service.DishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class IngredientServiceImplTest {

    private IngredientRepository ingredientRepository;
    private DishService dishService;
    private IngredientServiceImpl service;

    @BeforeEach
    void setUp() throws Exception {
        ingredientRepository = mock(IngredientRepository.class);
        dishService = mock(DishService.class);
        service = new IngredientServiceImpl();
        setField(service, "ingredientRepository", ingredientRepository);
        setField(service, "dishService", dishService);
    }

    @Test
    void saveCreatesNewIngredientForDish() {
        Dish dish = dish(5L);
        IngredientDTO input = new IngredientDTO(null, 5L, "Rice", new BigDecimal("100.00"), "g");
        when(dishService.findById(5L)).thenReturn(dish);
        when(ingredientRepository.save(any(Ingredient.class))).thenAnswer(invocation -> {
            Ingredient saved = invocation.getArgument(0);
            saved.setId(9L);
            return saved;
        });

        IngredientDTO result = service.save(input);

        assertEquals(9L, result.getId());
        assertEquals(5L, result.getDishId());
        assertEquals("Rice", result.getName());
        assertEquals(new BigDecimal("100.00"), result.getQuantityG());
        assertEquals("g", result.getUnit());
    }

    @Test
    void saveUpdatesExistingIngredientWhenIdExists() {
        Dish dish = dish(5L);
        Ingredient existing = ingredient(3L, dish, "Old", new BigDecimal("50.00"), "g");
        IngredientDTO input = new IngredientDTO(3L, 5L, "New", new BigDecimal("75.00"), "ml");
        when(dishService.findById(5L)).thenReturn(dish);
        when(ingredientRepository.findById(3L)).thenReturn(Optional.of(existing));
        when(ingredientRepository.save(existing)).thenReturn(existing);

        IngredientDTO result = service.save(input);

        assertEquals(3L, result.getId());
        assertEquals("New", result.getName());
        assertEquals(new BigDecimal("75.00"), result.getQuantityG());
        assertEquals("ml", result.getUnit());
    }

    @Test
    void findByIdReturnsMappedIngredientOrThrows() {
        Ingredient ingredient = ingredient(1L, dish(2L), "Salt", new BigDecimal("5.00"), "g");
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));
        when(ingredientRepository.findById(99L)).thenReturn(Optional.empty());

        assertEquals("Salt", service.findById(1L).getName());
        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void findByDishIdMapsAllIngredients() {
        when(ingredientRepository.findByDishId(2L)).thenReturn(List.of(
                ingredient(1L, dish(2L), "Salt", new BigDecimal("5.00"), "g"),
                ingredient(2L, dish(2L), "Rice", new BigDecimal("100.00"), "g")
        ));

        List<IngredientDTO> result = service.findByDishId(2L);

        assertEquals(2, result.size());
        assertEquals("Salt", result.get(0).getName());
        assertEquals("Rice", result.get(1).getName());
    }

    @Test
    void deleteChecksExistenceBeforeDeleting() {
        when(ingredientRepository.existsById(1L)).thenReturn(true);
        when(ingredientRepository.existsById(2L)).thenReturn(false);

        service.delete(1L);

        verify(ingredientRepository).deleteById(1L);
        assertThrows(ResourceNotFoundException.class, () -> service.delete(2L));
    }

    @Test
    void findAllAndSearchReturnMappedPages() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Ingredient> page = new PageImpl<>(List.of(ingredient(1L, dish(2L), "Rice", new BigDecimal("100.00"), "g")));
        when(ingredientRepository.findAll(pageable)).thenReturn(page);
        when(ingredientRepository.findByNameContainingIgnoreCase("ri", pageable)).thenReturn(page);

        assertEquals("Rice", service.findAll(pageable).getContent().get(0).getName());
        assertEquals("Rice", service.searchByName("ri", pageable).getContent().get(0).getName());
    }

    private static Dish dish(Long id) {
        Dish dish = new Dish();
        dish.setId(id);
        return dish;
    }

    private static Ingredient ingredient(Long id, Dish dish, String name, BigDecimal quantityG, String unit) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(id);
        ingredient.setDish(dish);
        ingredient.setName(name);
        ingredient.setQuantityG(quantityG);
        ingredient.setUnit(unit);
        return ingredient;
    }

    private static void setField(Object target, String name, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }
}
