package com.example.javaweb.meal_planner_system.converter;

// Module: Converter
import com.example.javaweb.meal_planner_system.dto.HealthProfileDTO;
import com.example.javaweb.meal_planner_system.entity.HealthProfile;

public final class HealthProfileConverter {
    private HealthProfileConverter() {}

    public static HealthProfileDTO toDTO(HealthProfile hp) {
        if (hp == null) return null;
        return new HealthProfileDTO(
            hp.getId(),
            hp.getFullName(),
            hp.getAge(),
            hp.getGender(),
            hp.getHeightCm(),
            hp.getWeightKg(),
            hp.getAvatarUrl()
        );
    }
}
