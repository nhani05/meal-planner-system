package com.example.javaweb.meal_planner_system.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum FeedbackStatus {
    PENDING,
    PROCESSING,
    RESOLVED;

    @JsonValue
    public String getValue() {
        return name().toLowerCase();
    }

    @JsonCreator
    public static FeedbackStatus fromValue(String value) {
        if (value == null) return null;
        for (FeedbackStatus status : FeedbackStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid FeedbackStatus: " + value);
    }
}
