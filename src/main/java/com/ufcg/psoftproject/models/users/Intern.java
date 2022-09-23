package com.ufcg.psoftproject.models.users;

import java.util.List;
import java.util.Set;

import com.ufcg.psoftproject.models.userstories.UserStorie;

public class Intern extends GenericRole implements UserRole {
	
	private final String roleName = "INTERN";
	
	public Intern() {
		// TODO Auto-generated constructor stub
	}
	
	public String getRoleName() {
		return roleName;
	}

	@Override
	protected String getPercentageByStage(Set<UserStorie> userStorieList) {
		return "";
	}
}
