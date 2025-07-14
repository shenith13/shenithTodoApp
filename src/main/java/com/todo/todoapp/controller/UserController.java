package com.todo.todoapp.controller;

import com.todo.todoapp.dto.AdminUserView;
import com.todo.todoapp.model.User;
import com.todo.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // only admin can fetch all the users
    @GetMapping("/all")
    public List<AdminUserView> getAllUsers() {
        return userRepository.findAll().stream()
                .map(u -> new AdminUserView(
                        u.getId(),
                        u.getUsername(),
                        u.getRole() != null ? u.getRole().getName() : ""
                ))
                .collect(Collectors.toList());
    }

    // fetching own tasks
    @GetMapping("/me")
    public AdminUserView getCurrentUser(@AuthenticationPrincipal UserDetails principal) {
        String username = principal.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new AdminUserView(
                user.getId(),
                user.getUsername(),
                user.getRole() != null ? user.getRole().getName() : ""
        );
    }
}
