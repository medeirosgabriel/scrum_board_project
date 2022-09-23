package com.ufcg.psoftproject.models.users;

public class ScrumMaster extends GenericRole implements UserRole {
	
	private final String roleName = "SCRUM_MASTER";
	
	public ScrumMaster() {
		// TODO Auto-generated constructor stub
	}

	public String getRoleName() {
		return roleName;
	}
}
