package com.todo.todoapp.dto;

public class UserRegisterRequest {
    private String username;
    private String password;
    private String role; // e.g. "ROLE_USER", "ROLE_ADMIN"
    private String email;

    public UserRegisterRequest() {}

    // ✅ Add email to the constructor
    public UserRegisterRequest(String username, String password, String role, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
