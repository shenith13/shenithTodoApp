package com.todo.todoapp.dto;

// This class is used to hold login credentials sent by the client
public class LoginRequest {

    private String username;
    private String password;


    public LoginRequest() {}

    // Constructor with fields
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
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
}
