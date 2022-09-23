package com.ufcg.psoftproject.repositories;

import org.springframework.stereotype.Repository;

import com.ufcg.psoftproject.models.tasks.Task;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TaskRepository {

    private Map<String, Task> tasks = new HashMap<>();

    public void addTask(Task task) {
        this.tasks.put(task.getId(), task);
    }

    public void deleteTask(String taskId) {
        this.tasks.remove(taskId);
    }

    public Task getTask(String taskId) {
        return this.tasks.get(taskId);
    }

    public void updateTask(String taskId, Task newTask) {
        this.tasks.replace(taskId, newTask);
    }

    public boolean containsTask(Task task) {
        return this.tasks.containsValue(task);
    }

    public List<Task> getAllTasks() {
        return this.tasks.values().stream().toList();
    }
}
