package com.example.javaweb.meal_planner_system.converter;

import com.example.javaweb.meal_planner_system.entity.enums.FeedbackStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FeedbackStatusConverterTest {

    private final FeedbackStatusConverter converter = new FeedbackStatusConverter();

    @Test
    void convertToDatabaseColumnStoresUppercaseEnumName() {
        assertEquals("PENDING", converter.convertToDatabaseColumn(FeedbackStatus.PENDING));
        assertEquals("PROCESSING", converter.convertToDatabaseColumn(FeedbackStatus.PROCESSING));
        assertEquals("RESOLVED", converter.convertToDatabaseColumn(FeedbackStatus.RESOLVED));
    }

    @Test
    void convertToEntityAttributeReadsUppercaseAndLowercaseValues() {
        assertEquals(FeedbackStatus.PENDING, converter.convertToEntityAttribute("PENDING"));
        assertEquals(FeedbackStatus.PROCESSING, converter.convertToEntityAttribute("processing"));
        assertEquals(FeedbackStatus.RESOLVED, converter.convertToEntityAttribute("Resolved"));
    }

    @Test
    void converterReturnsNullForNullValues() {
        assertNull(converter.convertToDatabaseColumn(null));
        assertNull(converter.convertToEntityAttribute(null));
    }

    @Test
    void convertToEntityAttributeRejectsInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> converter.convertToEntityAttribute("unknown"));
    }
}
