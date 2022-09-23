package com.ufcg.psoftproject.models.users;

import java.util.List;
import java.util.Set;

import com.ufcg.psoftproject.models.userstories.UserStorie;

public interface UserRole {
	
	public String getRoleName();

    String generateDescription(Set<UserStorie> usList, String userId);
}
