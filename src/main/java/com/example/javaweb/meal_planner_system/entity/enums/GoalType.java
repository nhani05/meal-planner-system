package com.example.javaweb.meal_planner_system.entity.enums;

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

    public String getValue() {
        return value;
    }
}
