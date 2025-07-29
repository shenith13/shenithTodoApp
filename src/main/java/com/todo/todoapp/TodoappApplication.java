package com.todo.todoapp;

import com.todo.todoapp.util.LogDirInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TodoappApplication {
	public static void main(String[] args) {
		LogDirInitializer.createLogDirectory(); // 👈 Ensures log folder exists
		SpringApplication.run(TodoappApplication.class, args);
	}
}
