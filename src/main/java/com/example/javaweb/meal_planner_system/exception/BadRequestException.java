package com.example.javaweb.meal_planner_system.exception;

// Module: Exception
public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }
}
