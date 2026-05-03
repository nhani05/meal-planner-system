package com.example.javaweb.meal_planner_system.controller;

// Module: Controller

import com.example.javaweb.meal_planner_system.dto.LoginDTO;
import com.example.javaweb.meal_planner_system.dto.RegisterDTO;
import com.example.javaweb.meal_planner_system.entity.UserAccount;
import com.example.javaweb.meal_planner_system.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for user authentication and account management
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserAccountService userAccountService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        if (registerDTO.getPassword() == null || !registerDTO.getPassword().equals(registerDTO.getPasswordConfirm())) {
            throw new com.example.javaweb.meal_planner_system.exception.BadRequestException("Passwords do not match");
        }

        if (userAccountService.usernameExists(registerDTO.getUsername())) {
            throw new com.example.javaweb.meal_planner_system.exception.BadRequestException("Username already exists");
        }

        if (userAccountService.emailExists(registerDTO.getEmail())) {
            throw new com.example.javaweb.meal_planner_system.exception.BadRequestException("Email already exists");
        }

        UserAccount userAccount = userAccountService.register(registerDTO);
        return ResponseEntity.ok(userAccountService.convertToDTO(userAccount));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        var user = userAccountService.findByUsername(loginDTO.getUsername());
        if (user.isPresent()) {
            return ResponseEntity.ok(userAccountService.convertToDTO(user.get()));
        }
        throw new com.example.javaweb.meal_planner_system.exception.BadRequestException("Invalid username or password");
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        UserAccount user = userAccountService.findById(id); // will throw if not found
        return ResponseEntity.ok(userAccountService.convertToDTO(user));
    }
}
