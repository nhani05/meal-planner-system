package com.example.javaweb.meal_planner_system.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Activity level enumeration
 */
public enum ActivityLevel {
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high");

    private final String value;

    ActivityLevel(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ActivityLevel fromValue(String value) {
        if (value == null) return null;
        for (ActivityLevel level : ActivityLevel.values()) {
            if (level.value.equalsIgnoreCase(value)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid ActivityLevel value: " + value);
    }
}
