package com.example.javaweb.meal_planner_system.converter;

// Module: Converter
import com.example.javaweb.meal_planner_system.dto.UserAccountDTO;
import com.example.javaweb.meal_planner_system.entity.UserAccount;

public final class UserAccountConverter {
    private UserAccountConverter() {}

    public static UserAccountDTO toDTO(UserAccount u) {
        if (u == null) return null;
        return new UserAccountDTO(
            u.getId(),
            u.getUsername(),
            u.getEmail(),
            u.getRole(),
            u.getStatus()
        );
    }
}
