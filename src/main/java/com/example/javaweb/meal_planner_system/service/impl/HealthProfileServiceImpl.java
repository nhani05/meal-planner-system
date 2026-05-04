package com.example.javaweb.meal_planner_system.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.javaweb.meal_planner_system.dto.HealthProfileDTO;
import com.example.javaweb.meal_planner_system.entity.HealthProfile;
import com.example.javaweb.meal_planner_system.entity.UserAccount;
import com.example.javaweb.meal_planner_system.repository.HealthProfileRepository;
import com.example.javaweb.meal_planner_system.service.HealthProfileService;
import com.example.javaweb.meal_planner_system.service.UserAccountService;

// Module: Service
@Service
public class HealthProfileServiceImpl implements HealthProfileService {

    @Autowired
    private HealthProfileRepository healthProfileRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Override
    public HealthProfile save(HealthProfile healthProfile) {
        return healthProfileRepository.save(healthProfile);
    }

    @Override
    public Optional<HealthProfile> findByAccountId(Long accountId) {
        return healthProfileRepository.findByAccountId(accountId);
    }

    @Override
    public HealthProfileDTO convertToDTO(HealthProfile healthProfile) {
        return com.example.javaweb.meal_planner_system.converter.HealthProfileConverter.toDTO(healthProfile);
    }

    @Override
    public HealthProfile findById(Long id) {
        if (id == null) {
            throw new com.example.javaweb.meal_planner_system.exception.BadRequestException("HealthProfile id must not be null");
        }
        return healthProfileRepository.findById(id)
                .orElseThrow(() -> new com.example.javaweb.meal_planner_system.exception.ResourceNotFoundException("HealthProfile not found with id " + id));
    }

    @Override
    public HealthProfileDTO createOrUpdateForAccount(Long accountId, HealthProfileDTO healthProfileDTO) {
        UserAccount account = userAccountService.findById(accountId);

        HealthProfile profile = healthProfileRepository.findByAccountId(accountId).orElse(new HealthProfile());
        profile.setAccount(account);
        profile.setFullName(healthProfileDTO.getFullName());
        profile.setAge(healthProfileDTO.getAge());
        profile.setGender(healthProfileDTO.getGender());
        profile.setHeightCm(healthProfileDTO.getHeightCm());
        profile.setWeightKg(healthProfileDTO.getWeightKg());
        profile.setAvatarUrl(healthProfileDTO.getAvatarUrl());

        HealthProfile saved = healthProfileRepository.save(profile);
        return convertToDTO(saved);
    }
}
