package com.example.javaweb.meal_planner_system.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum FeedbackStatus {
    PENDING("pending"),
    PROCESSING("processing"),
    RESOLVED("resolved");

    private final String value;

    FeedbackStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static FeedbackStatus fromValue(String value) {
        if (value == null) return null;
        for (FeedbackStatus status : FeedbackStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid FeedbackStatus: " + value);
    }
}
