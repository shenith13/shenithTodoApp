package com.todo.todoapp.dto;

// This class is used to send the JWT token and role back to the client after login
public class LoginResponse {

    private String token;
    private String role;

    // Default constructor
    public LoginResponse() {}

    // Constructor with all fields
    public LoginResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
