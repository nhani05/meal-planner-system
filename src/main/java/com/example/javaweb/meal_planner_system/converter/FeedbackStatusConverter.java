package com.example.javaweb.meal_planner_system.converter;

import com.example.javaweb.meal_planner_system.entity.enums.FeedbackStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA Converter for FeedbackStatus enum.
 * Handles both uppercase (standard) and lowercase (legacy) values from database.
 */
@Converter(autoApply = true)
public class FeedbackStatusConverter implements AttributeConverter<FeedbackStatus, String> {

    @Override
    public String convertToDatabaseColumn(FeedbackStatus status) {
        if (status == null) return null;
        return status.name(); // Store as uppercase in DB
    }

    @Override
    public FeedbackStatus convertToEntityAttribute(String value) {
        if (value == null) return null;
        // Support both uppercase and lowercase values from DB
        for (FeedbackStatus status : FeedbackStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid FeedbackStatus value: " + value);
    }
}
