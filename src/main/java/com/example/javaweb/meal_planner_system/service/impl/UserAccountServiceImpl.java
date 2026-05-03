package com.example.javaweb.meal_planner_system.service.impl;

import com.example.javaweb.meal_planner_system.dto.UserAccountDTO;
import com.example.javaweb.meal_planner_system.dto.RegisterDTO;
import com.example.javaweb.meal_planner_system.entity.UserAccount;
import com.example.javaweb.meal_planner_system.entity.enums.UserRole;
import com.example.javaweb.meal_planner_system.entity.enums.UserStatus;
import com.example.javaweb.meal_planner_system.repository.UserAccountRepository;
import com.example.javaweb.meal_planner_system.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

// Module: Service
@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserAccount register(RegisterDTO registerDTO) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(registerDTO.getUsername());
        userAccount.setEmail(registerDTO.getEmail());
        userAccount.setPasswordHash(passwordEncoder.encode(registerDTO.getPassword()));
        userAccount.setRole(UserRole.USER);
        userAccount.setStatus(UserStatus.ACTIVE);
        return userAccountRepository.save(userAccount);
    }

    @Override
    public Optional<UserAccount> findByUsername(String username) {
        return userAccountRepository.findByUsername(username);
    }

    @Override
    public Optional<UserAccount> findByEmail(String email) {
        return userAccountRepository.findByEmail(email);
    }

    @Override
    public UserAccount findById(Long id) {
        if (id == null) {
            throw new com.example.javaweb.meal_planner_system.exception.BadRequestException("UserAccount id must not be null");
        }
        return userAccountRepository.findById(id)
                .orElseThrow(() -> new com.example.javaweb.meal_planner_system.exception.ResourceNotFoundException("UserAccount not found with id " + id));
    }

    @Override
    public UserAccountDTO convertToDTO(UserAccount userAccount) {
        return com.example.javaweb.meal_planner_system.converter.UserAccountConverter.toDTO(userAccount);
    }

    @Override
    public boolean usernameExists(String username) {
        return userAccountRepository.existsByUsername(username);
    }

    @Override
    public boolean emailExists(String email) {
        return userAccountRepository.existsByEmail(email);
    }
}
