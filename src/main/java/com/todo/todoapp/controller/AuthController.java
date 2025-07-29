package com.todo.todoapp.controller;

import com.todo.todoapp.dto.LoginRequest;
import com.todo.todoapp.dto.LoginResponse;
import com.todo.todoapp.dto.UserRegisterRequest;
import com.todo.todoapp.model.Role;
import com.todo.todoapp.model.User;
import com.todo.todoapp.repository.RoleRepository;
import com.todo.todoapp.repository.UserRepository;
import com.todo.todoapp.security.JwtUtil;
import com.todo.todoapp.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    // ✅ LOGIN
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String token = jwtUtil.generateToken(request.getUsername());

        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
        String role = userOpt.map(u -> u.getRole().getName()).orElse("UNKNOWN");

        return new LoginResponse(token, role);
    }

    // ✅ REGISTER
    @PostMapping("/register")
    public String register(@RequestBody UserRegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return "Username already exists!";
        }

        Role role = roleRepository.findByName(request.getRole());
        if (role == null) {
            return "Invalid role: " + request.getRole();
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        user.setEmail(request.getEmail());

        userRepository.save(user);

        // ✅ Send welcome email to the real user email
        emailService.sendWelcomeEmail(user.getEmail(), user.getUsername());

        return "User registered successfully!";
    }
}
