package com.example.javaweb.meal_planner_system.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleNotFound_returns404WithErrorBody() {
        ResponseEntity<ErrorResponse> response =
                handler.handleNotFound(new ResourceNotFoundException("Dish not found"));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("Dish not found", response.getBody().getMessage());
        assertTrue(response.getBody().getTimestamp() > 0);
    }

    @Test
    void handleBadRequest_returns400WithErrorBody() {
        ResponseEntity<ErrorResponse> response =
                handler.handleBadRequest(new BadRequestException("Invalid portion"));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Invalid portion", response.getBody().getMessage());
        assertTrue(response.getBody().getTimestamp() > 0);
    }

    @Test
    void handleAll_returns500WithGenericMessageAndOriginalDetails() {
        PrintStream originalErr = System.err;
        ResponseEntity<ErrorResponse> response;
        try {
            System.setErr(new PrintStream(new ByteArrayOutputStream()));
            response = handler.handleAll(new IllegalStateException("Unexpected state"));
        } finally {
            System.setErr(originalErr);
        }

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().getStatus());
        assertEquals("Internal server error: Unexpected state", response.getBody().getMessage());
        assertTrue(response.getBody().getTimestamp() > 0);
    }
}
