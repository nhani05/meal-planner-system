package com.example.javaweb.meal_planner_system.dto;

import lombok.Data;

@Data
public class AdminStatsDTO {
    private long totalUsers;
    private long totalDishes;
    private long activePlansToday;
    private long newFeedbacks;
}
