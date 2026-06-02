package com.example.javaweb.meal_planner_system.security;

import com.example.javaweb.meal_planner_system.entity.UserAccount;
import com.example.javaweb.meal_planner_system.entity.enums.UserRole;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtUtilTest {

    @Test
    void generatedTokenCanBeValidatedAndParsed() throws Exception {
        JwtUtil jwtUtil = jwtUtil("01234567890123456789012345678901", 86_400_000L);
        UserAccount user = new UserAccount();
        user.setId(42L);
        user.setUsername("john");
        user.setRole(UserRole.ADMIN);

        String token = jwtUtil.generateToken(user);

        assertTrue(jwtUtil.validateToken(token));
        assertEquals("john", jwtUtil.getUsernameFromToken(token));
        assertEquals(42L, jwtUtil.extractUserId(token));
    }

    @Test
    void validateTokenReturnsFalseForMalformedToken() throws Exception {
        JwtUtil jwtUtil = jwtUtil("01234567890123456789012345678901", 86_400_000L);

        assertFalse(jwtUtil.validateToken("not-a-token"));
    }

    @Test
    void validateTokenReturnsFalseForExpiredToken() throws Exception {
        JwtUtil jwtUtil = jwtUtil("01234567890123456789012345678901", -1L);
        UserAccount user = new UserAccount();
        user.setId(7L);
        user.setUsername("expired-user");
        user.setRole(UserRole.USER);

        String token = jwtUtil.generateToken(user);

        assertFalse(jwtUtil.validateToken(token));
    }

    private static JwtUtil jwtUtil(String secret, long expirationMs) throws Exception {
        JwtUtil jwtUtil = new JwtUtil();
        setField(jwtUtil, "jwtSecret", secret);
        setField(jwtUtil, "jwtExpirationMs", expirationMs);
        return jwtUtil;
    }

    private static void setField(Object target, String name, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }
}
