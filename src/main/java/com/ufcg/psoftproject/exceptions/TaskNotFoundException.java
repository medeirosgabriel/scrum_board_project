package com.ufcg.psoftproject.exceptions;

public class TaskNotFoundException extends Exception {
    private static final long SerialVerdsionUID = 5L;

    public TaskNotFoundException(String msg) {
        super(msg);
    }
}
