package com.ufcg.psoftproject.models.tasks;

import java.util.UUID;

import com.ufcg.psoftproject.models.userstories.UserStorie;

public class Task {

    private String id;
    private String title;
    private String description;
    private UserStorie linkedUserStorie;
    private TaskState state;

    public Task(String title, String description, UserStorie linkedUserStorie) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.linkedUserStorie = linkedUserStorie;
        this.state = new NotDone(this);
    }

    public String getTitle() {
        return this.title;
    }

    public String getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public TaskState getState() {
        return this.state;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setState() {
        this.state.updateStatus();
    }

    public void updateState() {
        this.state.updateStatus();
    }

    public void setState(TaskState state) {
        this.state = state;
    }

}
