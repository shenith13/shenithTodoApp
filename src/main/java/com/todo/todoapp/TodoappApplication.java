package com.todo.todoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling; // 👉 Add this

@SpringBootApplication
@EnableScheduling // 👉 This enables your scheduler class
public class TodoappApplication {
	public static void main(String[] args) {
		SpringApplication.run(TodoappApplication.class, args);
	}
}
