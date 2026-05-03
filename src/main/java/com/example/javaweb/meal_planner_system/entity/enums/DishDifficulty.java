package com.example.javaweb.meal_planner_system.entity.enums;

/**
 * Dish difficulty enumeration
 */
public enum DishDifficulty {
    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard");

    private final String value;

    DishDifficulty(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
