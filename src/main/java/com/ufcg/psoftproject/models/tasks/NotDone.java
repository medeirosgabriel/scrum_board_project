package com.ufcg.psoftproject.models.tasks;

public class NotDone implements TaskState {
    private Task task;

    public NotDone(Task task) {
        this.task = task;
    }

    public void updateStatus() {
        this.task.setState(new Done(this.task));
    }

    public String getStatus() {
        return "NOT_DONE";
    }

}
