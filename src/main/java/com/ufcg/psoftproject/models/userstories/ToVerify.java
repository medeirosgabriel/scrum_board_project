package com.ufcg.psoftproject.models.userstories;

public class ToVerify implements UserStorieState {
	
	private UserStorie userStorie;
	private final String name = "TO_VERIFY";

	public ToVerify(UserStorie userStorie) {
		this.userStorie = userStorie;
	}

	@Override
	public void changeState() {
		this.userStorie.setState(new DoneUs(this.userStorie));
	}

	@Override
	public String getName() {
		return this.name;
	}

}
