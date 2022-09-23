package com.ufcg.psoftproject.exceptions;

public class UserNotAuthorizedException extends Exception {
	
	private static final long serialVersionUID = 2L;
	
	public UserNotAuthorizedException(String msg) {
		super(msg);
	}

}
