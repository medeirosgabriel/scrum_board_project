package com.ufcg.psoftproject.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import com.ufcg.psoftproject.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoftproject.dtos.ProjectDTO;
import com.ufcg.psoftproject.models.Project;
import com.ufcg.psoftproject.services.ProjectService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value = "/project/", method = RequestMethod.GET)
	public ResponseEntity<?> getAllProjects(@RequestParam("idScrumMaster") String idSM) {
		List<Project> projects;
		try {
			projects = this.projectService.getAllProjects(idSM);
			return new ResponseEntity<List<Project>>(projects, HttpStatus.OK);
		} catch (UserNotAuthorizedException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(value = "/project/addUser", method = RequestMethod.PUT)
	public ResponseEntity<?> addUserToProject(@RequestParam("idScrumMaster") String idSM, @RequestParam("idProject") String idProject, @RequestParam("idUser") String idUser,
			@RequestParam("role") String role) {
		
		try {
			this.projectService.addUser(idSM, idProject, idUser, role);
			return new ResponseEntity<String>("User successfully added to the project", HttpStatus.OK);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<String>("Project not found", HttpStatus.NOT_FOUND);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
		} catch (UserNotAuthorizedException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
		} catch (DuplicateLinkedUserException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (UserRoleNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/project/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getProjectById(@RequestParam("idScrumMaster") String idSM, @PathVariable("id") String id) {
		Project project;
		try {
			project = this.projectService.getProjectById(idSM, id);
			return new ResponseEntity<Project>(project, HttpStatus.OK);
		} catch (UserNotAuthorizedException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/project/", method = RequestMethod.POST)
	public ResponseEntity<?> createProject(@RequestParam("idScrumMaster") String idSM, @RequestBody ProjectDTO project) {
		Project newProject;
		try {
			newProject = this.projectService.createProject(idSM, project);
			return new ResponseEntity<Project>(newProject, HttpStatus.OK);
		} catch (UserNotAuthorizedException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(value = "/project/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteProject(@RequestParam("idScrumMaster") String idSM, @PathVariable("id") String id) {
		try {
			this.projectService.deleteProject(idSM, id);
			return new ResponseEntity<String>("Project Removed: " + id, HttpStatus.OK);
		} catch (UserNotAuthorizedException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/project/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateProject(@RequestParam("idScrumMaster") String idSM, @PathParam("id") String projectId, @RequestBody ProjectDTO project) {
		try {
			this.projectService.updateProject(idSM, projectId, project);
			return new ResponseEntity<String>("Project Updated: " + projectId, HttpStatus.OK);
		} catch (UserNotAuthorizedException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/project/UsReport", method = RequestMethod.GET)
	public ResponseEntity<?> generateUsDescription(@RequestParam("idProject") String idProject, @RequestParam("idUser") String idUser) {
		String finalResult;
		try {
			 finalResult = this.projectService.generateDescriptionAboutUs(idProject, idUser);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (UserNotAuthorizedException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
		} return new ResponseEntity<String>(finalResult, HttpStatus.OK);
	}

	@RequestMapping(value = "/project/MasterUsReport", method = RequestMethod.GET)
	public ResponseEntity<?> generateMasterUsDescription(@RequestParam("idProject") String idProject, @RequestParam("idScrumMaster") String idSM) {
		List<String> finalResult;
		try {
			finalResult = this.projectService.generateMasterDescriptionAboutUs(idProject, idSM);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (UserNotAuthorizedException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
		} return new ResponseEntity<List<String>>(finalResult, HttpStatus.OK);
	}
}
