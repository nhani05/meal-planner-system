package com.example.javaweb.meal_planner_system.exception;

// Module: Exception
public class ErrorResponse {
    private int status;
    private String message;
    private long timestamp;

    public ErrorResponse() {}

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
