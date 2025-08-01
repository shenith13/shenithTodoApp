package com.todo.todoapp.controller;

import com.todo.todoapp.dto.AdminUserView;
import com.todo.todoapp.model.Role;
import com.todo.todoapp.model.User;
import com.todo.todoapp.repository.RoleRepository;
import com.todo.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    // ✅ Only admin can fetch all users
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

    // ✅ Authenticated user can fetch their own info
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

    // ✅ Only admin can update user info
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateUser(@PathVariable Long id, @RequestBody AdminUserView updatedUser) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return "User not found!";
        }

        User user = userOpt.get();
        user.setUsername(updatedUser.getUsername());

        if (updatedUser.getRole() != null && !updatedUser.getRole().isEmpty()) {
            Role newRole = roleRepository.findByName(updatedUser.getRole());
            if (newRole != null) {
                user.setRole(newRole);
            } else {
                return "Invalid role: " + updatedUser.getRole();
            }
        }

        userRepository.save(user);
        return "User updated successfully!";
    }

    // ✅ Only admin can delete a user
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@PathVariable Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return "User not found!";
        }

        userRepository.deleteById(id);
        return "User deleted successfully!";
    }
}
