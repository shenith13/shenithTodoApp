package com.todo.todoapp.service;

import com.todo.todoapp.model.Task;
import com.todo.todoapp.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
public class TaskStatusScheduler {

    private static final Logger logger = LoggerFactory.getLogger(TaskStatusScheduler.class);

    @Autowired
    private TaskRepository taskRepository;

    @Scheduled(fixedRate = 60000) // Every 1 minute
    public void updateExpiredTasks() {
        logger.info("Running scheduled task: updateExpiredTasks");

        try {
            List<Task> tasks = taskRepository.findAll();
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));

            for (Task task : tasks) {
                if (task.getEndDate() != null &&
                        task.getEndDate().isBefore(now) &&
                        !"Completed".equalsIgnoreCase(task.getStatus())) {

                    logger.info("Marking task '{}' (ID: {}) as Incomplete", task.getTitle(), task.getId());
                    task.setStatus("Incomplete");
                    taskRepository.save(task);
                }
            }

            logger.info("Scheduled task completed: updateExpiredTasks");
        } catch (Exception e) {
            logger.error("Error occurred during scheduled task updateExpiredTasks: {}", e.getMessage(), e);
        }
    }
}
