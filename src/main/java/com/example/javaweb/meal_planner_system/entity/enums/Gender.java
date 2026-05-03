package com.example.javaweb.meal_planner_system.entity.enums;

/**
 * Gender enumeration
 */
public enum Gender {
    MALE("male"),
    FEMALE("female"),
    OTHER("other");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
