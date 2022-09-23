package com.ufcg.psoftproject.models.users;

import java.util.List;
import java.util.Set;

import com.ufcg.psoftproject.models.userstories.NotifyEvent;
import com.ufcg.psoftproject.models.userstories.UserStorie;

public class LinkedUser implements Listener {
	
	private User user;
	private UserRole userRole;
	
	public LinkedUser(User user, UserRole userRole) {
		super();
		this.user = user;
		this.userRole = userRole;
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public UserRole getUserRole() {
		return userRole;
	}
	
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	@Override
	public void notifica(NotifyEvent event) {
		System.out.println("User " + this.user.getEmail() + " notified. Reason: " + event.getReason());
	}

	public String generateDescription(Set<UserStorie> usList) {
		return this.userRole.generateDescription(usList, this.user.getEmail());
	}
}
