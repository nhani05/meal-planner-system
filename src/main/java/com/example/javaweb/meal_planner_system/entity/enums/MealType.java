package com.example.javaweb.meal_planner_system.entity.enums;

/**
 * Meal type enumeration
 */
public enum MealType {
    BREAKFAST("breakfast"),
    LUNCH("lunch"),
    DINNER("dinner"),
    SNACK("snack");

    private final String value;

    MealType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
