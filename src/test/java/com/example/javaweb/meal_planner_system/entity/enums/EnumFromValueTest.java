package com.example.javaweb.meal_planner_system.entity.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnumFromValueTest {

    @Test
    void fromValueAcceptsCaseInsensitiveSerializedValues() {
        assertEquals(ActivityLevel.VERY_ACTIVE, ActivityLevel.fromValue("VERY_ACTIVE"));
        assertEquals(DishDifficulty.MEDIUM, DishDifficulty.fromValue("Medium"));
        assertEquals(DishSource.CUSTOM, DishSource.fromValue("CUSTOM"));
        assertEquals(FeedbackStatus.PROCESSING, FeedbackStatus.fromValue("processing"));
        assertEquals(Gender.FEMALE, Gender.fromValue("Female"));
        assertEquals(GoalType.MUSCLE_GAIN, GoalType.fromValue("MUSCLE_GAIN"));
        assertEquals(MealType.BREAKFAST, MealType.fromValue("Breakfast"));
        assertEquals(UserRole.ADMIN, UserRole.fromValue("ADMIN"));
        assertEquals(UserStatus.LOCKED, UserStatus.fromValue("Locked"));
    }

    @Test
    void fromValueReturnsNullForNullInput() {
        assertNull(ActivityLevel.fromValue(null));
        assertNull(DishDifficulty.fromValue(null));
        assertNull(DishSource.fromValue(null));
        assertNull(FeedbackStatus.fromValue(null));
        assertNull(Gender.fromValue(null));
        assertNull(GoalType.fromValue(null));
        assertNull(MealType.fromValue(null));
        assertNull(UserRole.fromValue(null));
        assertNull(UserStatus.fromValue(null));
    }

    @Test
    void fromValueRejectsUnknownValues() {
        assertThrows(IllegalArgumentException.class, () -> ActivityLevel.fromValue("invalid"));
        assertThrows(IllegalArgumentException.class, () -> DishDifficulty.fromValue("invalid"));
        assertThrows(IllegalArgumentException.class, () -> DishSource.fromValue("invalid"));
        assertThrows(IllegalArgumentException.class, () -> FeedbackStatus.fromValue("invalid"));
        assertThrows(IllegalArgumentException.class, () -> Gender.fromValue("invalid"));
        assertThrows(IllegalArgumentException.class, () -> GoalType.fromValue("invalid"));
        assertThrows(IllegalArgumentException.class, () -> MealType.fromValue("invalid"));
        assertThrows(IllegalArgumentException.class, () -> UserRole.fromValue("invalid"));
        assertThrows(IllegalArgumentException.class, () -> UserStatus.fromValue("invalid"));
    }

    @Test
    void getValueReturnsApiSerializedValues() {
        assertEquals("low", ActivityLevel.LOW.getValue());
        assertEquals("easy", DishDifficulty.EASY.getValue());
        assertEquals("system", DishSource.SYSTEM.getValue());
        assertEquals("pending", FeedbackStatus.PENDING.getValue());
        assertEquals("male", Gender.MALE.getValue());
        assertEquals("maintain", GoalType.MAINTAIN.getValue());
        assertEquals("dinner", MealType.DINNER.getValue());
        assertEquals("user", UserRole.USER.getValue());
        assertEquals("active", UserStatus.ACTIVE.getValue());
    }
}
