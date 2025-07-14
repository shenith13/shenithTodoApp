package com.todo.todoapp.controller;

import com.todo.todoapp.model.Task;
import com.todo.todoapp.model.User;
import com.todo.todoapp.model.Supply;
import com.todo.todoapp.repository.TaskRepository;
import com.todo.todoapp.repository.UserRepository;
import com.todo.todoapp.repository.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SupplyRepository supplyRepository;

    @GetMapping("/user/{userId}")
    public List<Task> getTasksByUser(@PathVariable Long userId) {
        return taskRepository.findByUserId(userId);
    }

    @PostMapping("/create")
    public Task createTask(
            @RequestParam Long userId,
            @RequestBody Task task,
            @RequestParam(required = false) Long supplyId
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.setUser(user);
        task.setStatus("Pending");

        if (supplyId != null) {
            Supply supply = supplyRepository.findById(supplyId).orElse(null);
            task.setSupply(supply);
        }

        return taskRepository.save(task);
    }

    @PutMapping("/update/{taskId}")
    public Task updateTask(@PathVariable Long taskId, @RequestBody Task updatedTask) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStartDate(updatedTask.getStartDate());
        task.setEndDate(updatedTask.getEndDate());
        task.setStatus(updatedTask.getStatus());

        return taskRepository.save(task);
    }

    @DeleteMapping("/delete/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
