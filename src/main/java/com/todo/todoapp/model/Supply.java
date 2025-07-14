package com.todo.todoapp.model;

import jakarta.persistence.*;

// Represents a supply item in the system mapped to the "supplies" table
@Entity
@Table(name = "supplies")
public class Supply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Default constructor (required by JPA)
    public Supply() {}

    // Constructor with all fields
    public Supply(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and setters

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
}
