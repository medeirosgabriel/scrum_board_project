package com.ufcg.psoftproject.models.userstories;

public class WorkInProgress implements UserStorieState {
	
	private UserStorie userStorie;
	private final String name = "WORK_IN_PROGRESS";

	public WorkInProgress(UserStorie userStorie) {
		this.userStorie = userStorie;
	}

	@Override
	public void changeState() {
		this.userStorie.setState(new ToVerify(this.userStorie));
	}

	@Override
	public String getName() {
		return this.name;
	}

}
