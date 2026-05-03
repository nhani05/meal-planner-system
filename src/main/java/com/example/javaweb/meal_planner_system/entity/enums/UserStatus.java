package com.example.javaweb.meal_planner_system.entity.enums;

/**
 * User account status enumeration
 */
public enum UserStatus {
    ACTIVE("active"),
    LOCKED("locked"),
    DELETED("deleted");

    private final String value;

    UserStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
