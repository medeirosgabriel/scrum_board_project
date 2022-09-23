package com.ufcg.psoftproject.models.tasks;

public class Done implements TaskState {
    private Task task;

    public Done(Task task) {
        this.task = task;
    }

    public void updateStatus() {
        return;
    }

    public String getStatus() {
        return "DONE";
    }
}
