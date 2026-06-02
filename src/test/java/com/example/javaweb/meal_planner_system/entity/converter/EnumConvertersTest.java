package com.example.javaweb.meal_planner_system.entity.converter;

import com.example.javaweb.meal_planner_system.entity.enums.ActivityLevel;
import com.example.javaweb.meal_planner_system.entity.enums.DishDifficulty;
import com.example.javaweb.meal_planner_system.entity.enums.DishSource;
import com.example.javaweb.meal_planner_system.entity.enums.Gender;
import com.example.javaweb.meal_planner_system.entity.enums.GoalType;
import com.example.javaweb.meal_planner_system.entity.enums.MealType;
import com.example.javaweb.meal_planner_system.entity.enums.UserRole;
import com.example.javaweb.meal_planner_system.entity.enums.UserStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnumConvertersTest {

    @Test
    void convertersWriteSerializedDatabaseValues() {
        assertEquals("system", new EnumConverters.DishSourceConverter().convertToDatabaseColumn(DishSource.SYSTEM));
        assertEquals("hard", new EnumConverters.DishDifficultyConverter().convertToDatabaseColumn(DishDifficulty.HARD));
        assertEquals("snack", new EnumConverters.MealTypeConverter().convertToDatabaseColumn(MealType.SNACK));
        assertEquals("other", new EnumConverters.GenderConverter().convertToDatabaseColumn(Gender.OTHER));
        assertEquals("admin", new EnumConverters.UserRoleConverter().convertToDatabaseColumn(UserRole.ADMIN));
        assertEquals("deleted", new EnumConverters.UserStatusConverter().convertToDatabaseColumn(UserStatus.DELETED));
        assertEquals("maintenance", new EnumConverters.GoalTypeConverter().convertToDatabaseColumn(GoalType.MAINTENANCE));
        assertEquals("active", new EnumConverters.ActivityLevelConverter().convertToDatabaseColumn(ActivityLevel.ACTIVE));
    }

    @Test
    void convertersReadCaseInsensitiveDatabaseValues() {
        assertEquals(DishSource.CUSTOM, new EnumConverters.DishSourceConverter().convertToEntityAttribute("CUSTOM"));
        assertEquals(DishDifficulty.EASY, new EnumConverters.DishDifficultyConverter().convertToEntityAttribute("Easy"));
        assertEquals(MealType.LUNCH, new EnumConverters.MealTypeConverter().convertToEntityAttribute("LUNCH"));
        assertEquals(Gender.FEMALE, new EnumConverters.GenderConverter().convertToEntityAttribute("Female"));
        assertEquals(UserRole.USER, new EnumConverters.UserRoleConverter().convertToEntityAttribute("USER"));
        assertEquals(UserStatus.LOCKED, new EnumConverters.UserStatusConverter().convertToEntityAttribute("Locked"));
        assertEquals(GoalType.ENDURANCE, new EnumConverters.GoalTypeConverter().convertToEntityAttribute("ENDURANCE"));
        assertEquals(ActivityLevel.SEDENTARY, new EnumConverters.ActivityLevelConverter().convertToEntityAttribute("Sedentary"));
    }

    @Test
    void convertersReturnNullForNullValues() {
        assertNull(new EnumConverters.DishSourceConverter().convertToDatabaseColumn(null));
        assertNull(new EnumConverters.DishSourceConverter().convertToEntityAttribute(null));
        assertNull(new EnumConverters.DishDifficultyConverter().convertToDatabaseColumn(null));
        assertNull(new EnumConverters.MealTypeConverter().convertToDatabaseColumn(null));
        assertNull(new EnumConverters.GenderConverter().convertToDatabaseColumn(null));
        assertNull(new EnumConverters.UserRoleConverter().convertToDatabaseColumn(null));
        assertNull(new EnumConverters.UserStatusConverter().convertToDatabaseColumn(null));
        assertNull(new EnumConverters.GoalTypeConverter().convertToDatabaseColumn(null));
        assertNull(new EnumConverters.ActivityLevelConverter().convertToDatabaseColumn(null));
    }

    @Test
    void convertersRejectUnknownDatabaseValues() {
        assertThrows(IllegalArgumentException.class, () -> new EnumConverters.DishSourceConverter().convertToEntityAttribute("bad"));
        assertThrows(IllegalArgumentException.class, () -> new EnumConverters.DishDifficultyConverter().convertToEntityAttribute("bad"));
        assertThrows(IllegalArgumentException.class, () -> new EnumConverters.MealTypeConverter().convertToEntityAttribute("bad"));
        assertThrows(IllegalArgumentException.class, () -> new EnumConverters.GenderConverter().convertToEntityAttribute("bad"));
        assertThrows(IllegalArgumentException.class, () -> new EnumConverters.UserRoleConverter().convertToEntityAttribute("bad"));
        assertThrows(IllegalArgumentException.class, () -> new EnumConverters.UserStatusConverter().convertToEntityAttribute("bad"));
        assertThrows(IllegalArgumentException.class, () -> new EnumConverters.GoalTypeConverter().convertToEntityAttribute("bad"));
        assertThrows(IllegalArgumentException.class, () -> new EnumConverters.ActivityLevelConverter().convertToEntityAttribute("bad"));
    }
}
