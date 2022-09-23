package com.ufcg.psoftproject.exceptions;

public class DuplicateLinkedUserException extends Exception {
    private static final long serialVersionUID = 1L;

    public DuplicateLinkedUserException(String msg) {
        super(msg);
    }

}
