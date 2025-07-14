package com.todo.todoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.Set;


@Entity
@Table(name = "roles")
@JsonIgnoreProperties("users") // Prevent infinite loop during JSON serialization
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // One role can be assigned to many users
    @OneToMany(mappedBy = "role")
    private Set<User> users;

    // Default constructor (required by JPA)
    public Role() {}

    // Constructor with fields
    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
