package com.ufcg.psoftproject.exceptions;

public class UserRoleNotFoundException extends Exception{
    private static final long serialVersionUID = 5L;

    public UserRoleNotFoundException(String msg) {
        super(msg);
    }
}
