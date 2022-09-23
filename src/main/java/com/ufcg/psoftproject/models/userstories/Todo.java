package com.ufcg.psoftproject.models.userstories;

public class Todo implements UserStorieState {
	
	private UserStorie userStorie;
	private final String name = "TODO";

	public Todo(UserStorie userStorie) {
		super();
		this.userStorie = userStorie;
	}

	@Override
	public void changeState() {
		this.userStorie.setState(new WorkInProgress(this.userStorie));
	}

	@Override
	public String getName() {
		return this.name;
	}
	
}
