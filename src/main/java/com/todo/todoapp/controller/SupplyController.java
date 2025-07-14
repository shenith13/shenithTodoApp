package com.todo.todoapp.controller;

import com.todo.todoapp.model.Supply;
import com.todo.todoapp.repository.SupplyRepository;
import com.todo.todoapp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controller to manage Supply-related HTTP requests
@RestController
@RequestMapping("/api/supplies")
public class SupplyController {

    @Autowired
    private SupplyRepository supplyRepository;

    @Autowired
    private TaskRepository taskRepository;

    // Get all supplies
    @GetMapping("/all")
    public List<Supply> getAllSupplies() {
        return supplyRepository.findAll();
    }

    // Create a new supply
    @PostMapping("/create")
    public Supply createSupply(@RequestBody Supply supply) {
        return supplyRepository.save(supply);
    }

    // Update supply name by supply ID
    @PutMapping("/update/{id}")
    public Supply updateSupply(@PathVariable Long id, @RequestBody Supply updated) {
        Supply supply = supplyRepository.findById(id).orElseThrow();
        supply.setName(updated.getName());
        return supplyRepository.save(supply);
    }

    // Delete a supply by ID
    @DeleteMapping("/delete/{id}")
    public void deleteSupply(@PathVariable Long id) {
        Supply supply = supplyRepository.findById(id).orElse(null);

        if (supply != null) {
            taskRepository.findAll().stream()
                    .filter(task -> task.getSupply() != null && task.getSupply().getId().equals(id))
                    .forEach(task -> {
                        task.setSupply(null);
                        taskRepository.save(task);
                    });

            // Now delete the supply
            supplyRepository.deleteById(id);
        }
    }
}
