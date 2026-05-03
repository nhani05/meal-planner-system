package com.example.javaweb.meal_planner_system.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

public final class EnumConverters {
    private EnumConverters() {}

    @Converter(autoApply = false)
    public static class DishSourceConverter implements AttributeConverter<com.example.javaweb.meal_planner_system.entity.enums.DishSource, String> {
        @Override
        public String convertToDatabaseColumn(com.example.javaweb.meal_planner_system.entity.enums.DishSource attribute) {
            return attribute == null ? null : attribute.getValue();
        }

        @Override
        public com.example.javaweb.meal_planner_system.entity.enums.DishSource convertToEntityAttribute(String dbData) {
            if (dbData == null) return null;
            return Arrays.stream(com.example.javaweb.meal_planner_system.entity.enums.DishSource.values())
                    .filter(e -> e.getValue().equalsIgnoreCase(dbData))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown DishSource: " + dbData));
        }
    }

    @Converter(autoApply = false)
    public static class DishDifficultyConverter implements AttributeConverter<com.example.javaweb.meal_planner_system.entity.enums.DishDifficulty, String> {
        @Override
        public String convertToDatabaseColumn(com.example.javaweb.meal_planner_system.entity.enums.DishDifficulty attribute) {
            return attribute == null ? null : attribute.getValue();
        }

        @Override
        public com.example.javaweb.meal_planner_system.entity.enums.DishDifficulty convertToEntityAttribute(String dbData) {
            if (dbData == null) return null;
            return Arrays.stream(com.example.javaweb.meal_planner_system.entity.enums.DishDifficulty.values())
                    .filter(e -> e.getValue().equalsIgnoreCase(dbData))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown DishDifficulty: " + dbData));
        }
    }

    @Converter(autoApply = false)
    public static class MealTypeConverter implements AttributeConverter<com.example.javaweb.meal_planner_system.entity.enums.MealType, String> {
        @Override
        public String convertToDatabaseColumn(com.example.javaweb.meal_planner_system.entity.enums.MealType attribute) {
            return attribute == null ? null : attribute.getValue();
        }

        @Override
        public com.example.javaweb.meal_planner_system.entity.enums.MealType convertToEntityAttribute(String dbData) {
            if (dbData == null) return null;
            return Arrays.stream(com.example.javaweb.meal_planner_system.entity.enums.MealType.values())
                    .filter(e -> e.getValue().equalsIgnoreCase(dbData))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown MealType: " + dbData));
        }
    }

    @Converter(autoApply = false)
    public static class GenderConverter implements AttributeConverter<com.example.javaweb.meal_planner_system.entity.enums.Gender, String> {
        @Override
        public String convertToDatabaseColumn(com.example.javaweb.meal_planner_system.entity.enums.Gender attribute) {
            return attribute == null ? null : attribute.getValue();
        }

        @Override
        public com.example.javaweb.meal_planner_system.entity.enums.Gender convertToEntityAttribute(String dbData) {
            if (dbData == null) return null;
            return Arrays.stream(com.example.javaweb.meal_planner_system.entity.enums.Gender.values())
                    .filter(e -> e.getValue().equalsIgnoreCase(dbData))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown Gender: " + dbData));
        }
    }

    @Converter(autoApply = false)
    public static class UserRoleConverter implements AttributeConverter<com.example.javaweb.meal_planner_system.entity.enums.UserRole, String> {
        @Override
        public String convertToDatabaseColumn(com.example.javaweb.meal_planner_system.entity.enums.UserRole attribute) {
            return attribute == null ? null : attribute.getValue();
        }

        @Override
        public com.example.javaweb.meal_planner_system.entity.enums.UserRole convertToEntityAttribute(String dbData) {
            if (dbData == null) return null;
            return Arrays.stream(com.example.javaweb.meal_planner_system.entity.enums.UserRole.values())
                    .filter(e -> e.getValue().equalsIgnoreCase(dbData))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown UserRole: " + dbData));
        }
    }

    @Converter(autoApply = false)
    public static class UserStatusConverter implements AttributeConverter<com.example.javaweb.meal_planner_system.entity.enums.UserStatus, String> {
        @Override
        public String convertToDatabaseColumn(com.example.javaweb.meal_planner_system.entity.enums.UserStatus attribute) {
            return attribute == null ? null : attribute.getValue();
        }

        @Override
        public com.example.javaweb.meal_planner_system.entity.enums.UserStatus convertToEntityAttribute(String dbData) {
            if (dbData == null) return null;
            return Arrays.stream(com.example.javaweb.meal_planner_system.entity.enums.UserStatus.values())
                    .filter(e -> e.getValue().equalsIgnoreCase(dbData))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown UserStatus: " + dbData));
        }
    }

    @Converter(autoApply = false)
    public static class GoalTypeConverter implements AttributeConverter<com.example.javaweb.meal_planner_system.entity.enums.GoalType, String> {
        @Override
        public String convertToDatabaseColumn(com.example.javaweb.meal_planner_system.entity.enums.GoalType attribute) {
            return attribute == null ? null : attribute.getValue();
        }

        @Override
        public com.example.javaweb.meal_planner_system.entity.enums.GoalType convertToEntityAttribute(String dbData) {
            if (dbData == null) return null;
            return Arrays.stream(com.example.javaweb.meal_planner_system.entity.enums.GoalType.values())
                    .filter(e -> e.getValue().equalsIgnoreCase(dbData))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown GoalType: " + dbData));
        }
    }

    @Converter(autoApply = false)
    public static class ActivityLevelConverter implements AttributeConverter<com.example.javaweb.meal_planner_system.entity.enums.ActivityLevel, String> {
        @Override
        public String convertToDatabaseColumn(com.example.javaweb.meal_planner_system.entity.enums.ActivityLevel attribute) {
            return attribute == null ? null : attribute.getValue();
        }

        @Override
        public com.example.javaweb.meal_planner_system.entity.enums.ActivityLevel convertToEntityAttribute(String dbData) {
            if (dbData == null) return null;
            return Arrays.stream(com.example.javaweb.meal_planner_system.entity.enums.ActivityLevel.values())
                    .filter(e -> e.getValue().equalsIgnoreCase(dbData))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown ActivityLevel: " + dbData));
        }
    }
}
