package com.example.javaweb.meal_planner_system.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static DishSource fromValue(String value) {
        if (value == null) return null;
        for (DishSource source : DishSource.values()) {
            if (source.value.equalsIgnoreCase(value)) {
                return source;
            }
        }
        throw new IllegalArgumentException("Invalid DishSource value: " + value);
    }
}
