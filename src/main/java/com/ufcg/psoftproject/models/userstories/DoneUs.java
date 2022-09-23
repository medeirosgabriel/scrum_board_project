package com.ufcg.psoftproject.models.userstories;

public class DoneUs implements UserStorieState {
	
	private UserStorie userStorie;
	private final String name = "DONE";

	public DoneUs(UserStorie userStorie) {
		this.userStorie = userStorie;
	}

	@Override
	public void changeState() {}

	@Override
	public String getName() {
		return this.name;
	}

}
