package com.example.javaweb.meal_planner_system.entity.enums;

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

    public String getValue() {
        return value;
    }
}
