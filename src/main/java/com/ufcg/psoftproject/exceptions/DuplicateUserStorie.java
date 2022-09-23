package com.ufcg.psoftproject.exceptions;

public class DuplicateUserStorie extends Exception {

    private static final long serialVersionUID = 3L;

    public DuplicateUserStorie(String msg) {
        super(msg);
    }
}
