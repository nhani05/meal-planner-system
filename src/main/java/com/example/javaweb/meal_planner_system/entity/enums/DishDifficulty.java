package com.example.javaweb.meal_planner_system.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static DishDifficulty fromValue(String value) {
        if (value == null) return null;
        for (DishDifficulty difficulty : DishDifficulty.values()) {
            if (difficulty.value.equalsIgnoreCase(value)) {
                return difficulty;
            }
        }
        throw new IllegalArgumentException("Invalid DishDifficulty value: " + value);
    }
}
