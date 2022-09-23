package com.ufcg.psoftproject.models.users;

import java.util.List;
import java.util.Set;

import com.ufcg.psoftproject.models.userstories.UserStorie;

public class ProductOwner extends GenericRole implements UserRole {
	
	private final String roleName = "PRODUCT_OWNER";
	
	public ProductOwner() {
		// TODO Auto-generated constructor stub
	}
	
	public String getRoleName() {
		return roleName;
	}

	@Override
	protected String getPercentage(Set<UserStorie> usList, String userId) {
		return "";
	}

	@Override
	protected String getPercentageByStageAndUser(Set<UserStorie> userStories, String userId) {
		return "";
	}


}
