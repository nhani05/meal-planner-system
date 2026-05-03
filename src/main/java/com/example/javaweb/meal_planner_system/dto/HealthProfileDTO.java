package com.example.javaweb.meal_planner_system.dto;

import com.example.javaweb.meal_planner_system.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for HealthProfile
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthProfileDTO {
    private Long id;
    private String fullName;
    private Integer age;
    private Gender gender;
    private BigDecimal heightCm;
    private BigDecimal weightKg;
    private String avatarUrl;
}
