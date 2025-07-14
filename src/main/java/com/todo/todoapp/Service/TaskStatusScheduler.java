package com.todo.todoapp.Service;

import com.todo.todoapp.model.Task;
import com.todo.todoapp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
public class TaskStatusScheduler {

    @Autowired
    private TaskRepository taskRepository;

    @Scheduled(fixedRate = 60000) // Every 1 minute
    public void updateExpiredTasks() {
        List<Task> tasks = taskRepository.findAll();
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));

        for (Task task : tasks) {
            if (task.getEndDate() != null &&
                    task.getEndDate().isBefore(now) &&
                    !"Completed".equalsIgnoreCase(task.getStatus())) {

                task.setStatus("Incomplete");
                taskRepository.save(task);
            }
        }
    }
}
