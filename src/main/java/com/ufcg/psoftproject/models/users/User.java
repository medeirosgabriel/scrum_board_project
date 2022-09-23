package com.ufcg.psoftproject.models.users;

import java.util.Objects;

public class User {
	
	private String completeName;
	private String username;
	private String email;
	private boolean isScrumMaster;
	
	public User(String completeName, String username, String email, boolean isScrumMaster) {
		super();
		this.completeName = completeName;
		this.username = username;
		this.email = email;
		this.isScrumMaster = isScrumMaster;
	}

	public boolean isScrumMaster() {
		return isScrumMaster;
	}

	public void setScrumMaster(boolean isScrumMaster) {
		this.isScrumMaster = isScrumMaster;
	}

	public String getCompleteName() {
		return completeName;
	}
	
	public void setCompleteName(String completeName) {
		this.completeName = completeName;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email);
	}
}
