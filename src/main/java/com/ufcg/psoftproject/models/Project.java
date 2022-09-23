package com.ufcg.psoftproject.models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.ufcg.psoftproject.dtos.ProjectDTO;
import com.ufcg.psoftproject.models.users.LinkedUser;
import com.ufcg.psoftproject.models.userstories.UserStorie;

public class Project {
	
	private String id;
	private String name;
	private String description;
	private String institution;
	private Set<LinkedUser> users;
	private Set<UserStorie> userStories;
	
	public Project(ProjectDTO projectDTO) {
		super();
		this.id = UUID.randomUUID().toString();
		this.name = projectDTO.getName();
		this.description = projectDTO.getDescription();
		this.institution = projectDTO.getInstitution();
		this.users = new HashSet<>();
		this.userStories = new HashSet<>();
	}
	
	public Project(String name, String description, String institution) {
		super();
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.description = description;
		this.institution = institution;
		this.users = new HashSet<>();
		this.userStories = new HashSet<>();
	}
	
	public void addUserStorie(UserStorie userStorie) {
		this.userStories.add(userStorie);
	}

	public Set<UserStorie> getUserStories() {
		return userStories;
	}

	public void setUserStories(Set<UserStorie> userStories) {
		this.userStories = userStories;
	}

	public void setUsers(Set<LinkedUser> users) {
		this.users = users;
	}

	public LinkedUser getUser(String userId) {
		for (LinkedUser u : this.users) {
			if (userId.equals(u.getUser().getEmail())) {
				return u;
			}
		}
		return null;
	}
	
	public Set<LinkedUser> getUsers() {
		return users;
	}
	
	public void addUser(LinkedUser user) {
		users.add(user);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getInstitution() {
		return institution;
	}
	
	public void setInstitution(String institution) {
		this.institution = institution;
	}

	/*
	I don't think that's useful or relevant now, but...

	public String generateDescription(String idUser) {
		LinkedUser user = this.getUser(idUser);
		Set<UserStorie> usList = this.getUserStories();

		return user.generateDescription(usList);
	}
	 */
}
