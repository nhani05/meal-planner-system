package com.example.javaweb.meal_planner_system.service.impl;

import com.example.javaweb.meal_planner_system.dto.HealthProfileDTO;
import com.example.javaweb.meal_planner_system.entity.HealthProfile;
import com.example.javaweb.meal_planner_system.entity.UserAccount;
import com.example.javaweb.meal_planner_system.entity.enums.Gender;
import com.example.javaweb.meal_planner_system.exception.BadRequestException;
import com.example.javaweb.meal_planner_system.exception.ResourceNotFoundException;
import com.example.javaweb.meal_planner_system.repository.HealthProfileRepository;
import com.example.javaweb.meal_planner_system.service.UserAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HealthProfileServiceImplTest {

    private HealthProfileRepository healthProfileRepository;
    private UserAccountService userAccountService;
    private HealthProfileServiceImpl service;

    @BeforeEach
    void setUp() throws Exception {
        healthProfileRepository = mock(HealthProfileRepository.class);
        userAccountService = mock(UserAccountService.class);
        service = new HealthProfileServiceImpl();
        setField(service, "healthProfileRepository", healthProfileRepository);
        setField(service, "userAccountService", userAccountService);
    }

    @Test
    void saveAndFindByAccountIdDelegateToRepository() {
        HealthProfile profile = new HealthProfile();
        when(healthProfileRepository.save(profile)).thenReturn(profile);
        when(healthProfileRepository.findByAccountId(1L)).thenReturn(Optional.of(profile));

        assertSame(profile, service.save(profile));
        assertSame(profile, service.findByAccountId(1L).orElseThrow());
    }

    @Test
    void findByIdRejectsNullOrMissingId() {
        assertThrows(BadRequestException.class, () -> service.findById(null));

        when(healthProfileRepository.findById(9L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(9L));
    }

    @Test
    void findByIdReturnsExistingProfile() {
        HealthProfile profile = new HealthProfile();
        profile.setId(3L);
        when(healthProfileRepository.findById(3L)).thenReturn(Optional.of(profile));

        assertSame(profile, service.findById(3L));
    }

    @Test
    void createOrUpdateForAccountCreatesProfileWhenMissing() {
        UserAccount account = new UserAccount();
        account.setId(1L);
        HealthProfileDTO input = new HealthProfileDTO(null, "Lan", 30, Gender.FEMALE,
                new BigDecimal("160.00"), new BigDecimal("55.00"), "avatar.png");

        when(userAccountService.findById(1L)).thenReturn(account);
        when(healthProfileRepository.findByAccountId(1L)).thenReturn(Optional.empty());
        when(healthProfileRepository.save(any(HealthProfile.class))).thenAnswer(invocation -> {
            HealthProfile saved = invocation.getArgument(0);
            saved.setId(7L);
            return saved;
        });

        HealthProfileDTO result = service.createOrUpdateForAccount(1L, input);

        assertEquals(7L, result.getId());
        assertEquals("Lan", result.getFullName());
        assertEquals(30, result.getAge());
        assertEquals(Gender.FEMALE, result.getGender());
        assertEquals(new BigDecimal("160.00"), result.getHeightCm());
        assertEquals(new BigDecimal("55.00"), result.getWeightKg());
        assertEquals("avatar.png", result.getAvatarUrl());
    }

    @Test
    void createOrUpdateForAccountUpdatesExistingProfile() {
        UserAccount account = new UserAccount();
        account.setId(1L);
        HealthProfile existing = new HealthProfile();
        existing.setId(7L);
        HealthProfileDTO input = new HealthProfileDTO(7L, "Minh", 25, Gender.MALE,
                new BigDecimal("175.00"), new BigDecimal("70.00"), null);

        when(userAccountService.findById(1L)).thenReturn(account);
        when(healthProfileRepository.findByAccountId(1L)).thenReturn(Optional.of(existing));
        when(healthProfileRepository.save(existing)).thenReturn(existing);

        HealthProfileDTO result = service.createOrUpdateForAccount(1L, input);

        assertEquals(7L, result.getId());
        assertEquals("Minh", result.getFullName());
        assertEquals(25, result.getAge());
        assertEquals(Gender.MALE, result.getGender());
        assertEquals(new BigDecimal("175.00"), result.getHeightCm());
        assertEquals(new BigDecimal("70.00"), result.getWeightKg());
    }

    private static void setField(Object target, String name, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }
}
