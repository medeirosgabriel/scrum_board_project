package com.ufcg.psoftproject.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ufcg.psoftproject.models.users.LinkedUser;
import com.ufcg.psoftproject.models.users.User;
import com.ufcg.psoftproject.models.userstories.UserStorie;

import org.springframework.stereotype.Repository;

import com.ufcg.psoftproject.models.Project;

@Repository
public class ProjectRepository {
	
	private Map<String, Project> projects = new HashMap<String, Project>();
	
	public List<Project> getAllProjects() {
		return this.projects.values().stream().toList();
	}
	
	public void addUser(String projectId, LinkedUser user) {
		projects.get(projectId).addUser(user);
	}
	
	public Project getProjectById(String id) {
		return this.projects.get(id);
	}
	
	public Project createProject(Project project) {
		this.projects.put(project.getId(), project);
		return project;
	}
	
	public void deleteProject(String id) {
		this.projects.remove(id);
	}
	
	public void updateProject(String projectId, Project project) {
		this.projects.put(project.getId(), project);
	}

	public String generateDescription(Project project, String idUser) {
		LinkedUser user = project.getUser(idUser);
		Set<UserStorie> usList = project.getUserStories();

		return user.generateDescription(usList);
	}
}
