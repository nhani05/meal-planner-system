package com.example.javaweb.meal_planner_system.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Health goal type enumeration
 */
public enum GoalType {
    WEIGHT_LOSS("weight_loss"),
    MUSCLE_GAIN("muscle_gain"),
    MAINTAIN("maintain");

    private final String value;

    GoalType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static GoalType fromValue(String value) {
        if (value == null) return null;
        for (GoalType goal : GoalType.values()) {
            if (goal.value.equalsIgnoreCase(value)) {
                return goal;
            }
        }
        throw new IllegalArgumentException("Invalid GoalType value: " + value);
    }
}
