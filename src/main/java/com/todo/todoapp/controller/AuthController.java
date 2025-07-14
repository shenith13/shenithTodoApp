package com.todo.todoapp.controller;

import com.todo.todoapp.dto.LoginRequest;
import com.todo.todoapp.dto.LoginResponse;
import com.todo.todoapp.dto.UserRegisterRequest;
import com.todo.todoapp.model.Role;
import com.todo.todoapp.model.User;
import com.todo.todoapp.repository.RoleRepository;
import com.todo.todoapp.repository.UserRepository;
import com.todo.todoapp.security.JwtUtil;

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

    //  LOGIN
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        // Authenticate user
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Generate JWT
        String token = jwtUtil.generateToken(request.getUsername());

        // Get user's role
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
        String role = userOpt.map(u -> u.getRole().getName()).orElse("UNKNOWN");

        return new LoginResponse(token, role);
    }

    //  Register
    @PostMapping("/register")
    public String register(@RequestBody UserRegisterRequest request) {
        // Check if user already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return "Username already exists!";
        }

        // Fetch role from DB
        Role role = roleRepository.findByName(request.getRole());
        if (role == null) {
            return "Invalid role: " + request.getRole();
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        userRepository.save(user);

        return "User registered successfully!";
    }
}
