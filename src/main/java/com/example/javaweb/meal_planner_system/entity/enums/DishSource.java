package com.example.javaweb.meal_planner_system.entity.enums;

/**
 * Dish source enumeration
 */
public enum DishSource {
    SYSTEM("system"),
    CUSTOM("custom");

    private final String value;

    DishSource(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
