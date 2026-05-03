package com.example.javaweb.meal_planner_system.entity.enums;

/**
 * User role enumeration
 */
public enum UserRole {
    USER("user"),
    ADMIN("admin");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
