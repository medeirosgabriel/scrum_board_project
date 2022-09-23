package com.ufcg.psoftproject.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.ufcg.psoftproject.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoftproject.dtos.ProjectDTO;
import com.ufcg.psoftproject.models.Project;
import com.ufcg.psoftproject.models.users.LinkedUser;
import com.ufcg.psoftproject.models.users.User;
import com.ufcg.psoftproject.models.users.UserRole;
import com.ufcg.psoftproject.repositories.ProjectRepository;
import com.ufcg.psoftproject.repositories.UserRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;
	
	public List<Project> getAllProjects(String emailSM) throws UserNotAuthorizedException {
		if (!this.userService.verifyIsSM(emailSM)) {
			throw new UserNotAuthorizedException("You are not a Scrum Master");
		}
		return this.projectRepository.getAllProjects();
	}
	
	public void addUser(String emailSM, String projectId, String emailUser, String role) throws UserNotFoundException, ProjectNotFoundException,
			UserNotAuthorizedException, DuplicateLinkedUserException, UserRoleNotFoundException {
		if (!this.userService.verifyIsSM(emailSM)) {
			throw new UserNotAuthorizedException("You are not a Scrum Master");
		}
		Project project = this.projectRepository.getProjectById(projectId);
		if (project == null) {
			throw new ProjectNotFoundException("Project not found");
		}
		
		User user = this.userRepository.getUserByEmail(emailUser);
		if (user == null) {
			throw new UserNotFoundException("User not found");
		}

		LinkedUser linkedUser = project.getUser(emailUser);
		if (linkedUser != null) throw new DuplicateLinkedUserException("This user is already into this project");
		
		UserRole userRole = this.userService.getRoles().get(role);
		if (userRole == null) throw new UserRoleNotFoundException("Invalid user role: " + role);
		LinkedUser newLinkedUser = new LinkedUser(user, userRole);
		
		this.projectRepository.addUser(projectId, newLinkedUser);
	}
	
	public Project getProjectById(String emailSM, String projectId) throws UserNotAuthorizedException, ProjectNotFoundException {
		if (!this.userService.verifyIsSM(emailSM)) {
			throw new UserNotAuthorizedException("You are not a Scrum Master");
		}

		Project project = this.projectRepository.getProjectById(projectId);
		if (project == null) {
			throw new ProjectNotFoundException("Project not found");
		}
		return project;
	}
	
	public Project createProject(String emailSM, ProjectDTO project) throws UserNotAuthorizedException {
		if (!this.userService.verifyIsSM(emailSM)) {
			throw new UserNotAuthorizedException("You are not a Scrum Master");
		}
		Project newProject = new Project(project);
		newProject.addUser(new LinkedUser(this.userRepository.getUserByEmail(emailSM), this.userService.getRoles().get("SCRUM_MASTER")));
		this.projectRepository.createProject(newProject);
		return newProject;
	}
	
	public void deleteProject(String emailSM, String projectId) throws UserNotAuthorizedException, ProjectNotFoundException {
		if (!this.userService.verifyIsSM(emailSM)) {
			throw new UserNotAuthorizedException("Yout are not a Scrum Master");
		}
		Project project = this.projectRepository.getProjectById(projectId);
		if (project == null) {
			throw new ProjectNotFoundException("Project not found");
		}
		this.projectRepository.deleteProject(projectId);
	}
	
	public void updateProject(String emailSM, String projectId, ProjectDTO projectDTO) throws UserNotAuthorizedException, ProjectNotFoundException {
		if (!this.userService.verifyIsSM(emailSM)) {
			throw new UserNotAuthorizedException("Yout are not a Scrum Master");
		}
		Project project = this.projectRepository.getProjectById(projectId);
		if (project == null) {
			throw new ProjectNotFoundException("Project not found");
		}
		project.setDescription(projectDTO.getDescription());
		project.setInstitution(projectDTO.getInstitution());
		project.setName(projectDTO.getName());
		this.projectRepository.updateProject(projectId, project);
	}

	public String generateDescriptionAboutUs(String idProject, String idUser) throws ProjectNotFoundException, UserNotAuthorizedException {
		Project project = this.projectRepository.getProjectById(idProject);
		if (project == null) throw new ProjectNotFoundException("Project not found");

		LinkedUser user = project.getUser(idUser);
		if (user == null) throw new UserNotAuthorizedException("You are not authorized to realize this operation");

		return this.projectRepository.generateDescription(project, idUser);
	}

	public List<String> generateMasterDescriptionAboutUs(String idProject, String idMaster) throws ProjectNotFoundException, UserNotAuthorizedException {
		List<String> result = new ArrayList<>();

		Project project = this.projectRepository.getProjectById(idProject);
		if (project == null) throw new ProjectNotFoundException("Project not found");

		LinkedUser master = project.getUser(idMaster);
		if (master == null || master.getUser().isScrumMaster() == false) throw new UserNotAuthorizedException("You are not allowed to realize this operation");

		List<LinkedUser> usersList = project.getUsers().stream().toList();
		for (int i = 0; i < usersList.size(); i++) {
			result.add(this.projectRepository.generateDescription(project, usersList.get(i).getUser().getEmail()));
		}

		return result;
	}

}
