package com.ufcg.psoftproject.controllers;

import java.util.Set;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoftproject.dtos.UserStorieDTO;
import com.ufcg.psoftproject.exceptions.DuplicateUserStorie;
import com.ufcg.psoftproject.exceptions.ProjectNotFoundException;
import com.ufcg.psoftproject.exceptions.UserNotAuthorizedException;
import com.ufcg.psoftproject.exceptions.UserNotFoundException;
import com.ufcg.psoftproject.exceptions.UserStorieNotFound;
import com.ufcg.psoftproject.exceptions.UserStorieStateException;
import com.ufcg.psoftproject.models.userstories.UserStorie;
import com.ufcg.psoftproject.services.UserStorieService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserStorieController {
	
	@Autowired
	private UserStorieService userStorieService;
	
	@RequestMapping(value = "/us/", method = RequestMethod.POST)
	public ResponseEntity<?> addUserStorie(@RequestParam("userID") String userId, @RequestParam("projectId") String projectId, @RequestBody UserStorieDTO userStorieDTO) throws DuplicateUserStorie {
        UserStorie us;
		try {
			us = this.userStorieService.addUserStorie(userId, projectId, userStorieDTO);
			return new ResponseEntity<String>("User Storie created with the following ID: " + us.getId(), HttpStatus.OK);
		} catch (UserNotAuthorizedException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
    }

	@RequestMapping(value = "/us/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeUserStorie(@RequestParam("userID") String userId, @RequestParam("projectID") String projectId, @PathParam("usId") String usId) throws UserStorieNotFound {
		try {
			this.userStorieService.removeUserStorie(userId, projectId, usId);
			return new ResponseEntity<String>("Removed User Story: " + usId, HttpStatus.OK);
		} catch (UserStorieNotFound e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (UserNotAuthorizedException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
		}
    }
	
	@RequestMapping(value = "/us/", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUserStorie(@RequestParam("userID") String userId, @RequestParam("projectID") String projectId, 
    		@RequestParam("usId") String usId, @RequestBody UserStorieDTO userStorie) {
		try {
			this.userStorieService.updateUserStorie(userId, projectId, usId, userStorie);
			return new ResponseEntity<String>("Updated User Story: " + usId, HttpStatus.OK);
		} catch (UserStorieNotFound e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (UserNotAuthorizedException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
		}
    }
    
	@RequestMapping(value = "/us/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserStorie(@RequestParam("userID") String userId, @RequestParam("projectID") String projectId, @PathParam("id") String idUs) {
		try {
			return new ResponseEntity<>(this.userStorieService.getUserStorie(userId, projectId, idUs), HttpStatus.OK);
		} catch (UserStorieNotFound e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		} catch (UserNotAuthorizedException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
    }
    
	@RequestMapping(value = "/us/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllUserStories(@RequestParam("userID") String userId, @RequestParam("projectID") String projectId) throws UserStorieNotFound {
		try {
			return new ResponseEntity<Set<UserStorie>>(this.userStorieService.getAllUserStories(userId, projectId), HttpStatus.OK);
		} catch (UserStorieNotFound e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (UserNotAuthorizedException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
    }

	@RequestMapping(value = "/us/linkUser", method = RequestMethod.POST)
	public ResponseEntity<?> linkUserToUs(@RequestParam("UserID") String userId, @RequestParam("ProjectID") String projectID, @RequestParam("UserStorieID") String userStorieID) {
		try {
			this.userStorieService.linkUserToUs(userId, projectID, userStorieID);
		} catch (UserStorieNotFound e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (UserNotAuthorizedException e) {
			return new ResponseEntity<String>("This user does not exist or does not have a required role", HttpStatus.FORBIDDEN);
		} return new ResponseEntity<String>("User successfully joined the US", HttpStatus.OK);
	}

	@RequestMapping(value = "/us/masterLinkUser", method = RequestMethod.POST)
	public ResponseEntity<?> masterLinkUserToUs(@RequestParam("ScrumMasterID") String scrumMasterId, @RequestParam("UserID") String userId, @RequestParam("ProjectID") String projectId, @RequestParam("UserStorieID") String userStorieId) {
		try {
			this.userStorieService.masterLinkUserToUs(scrumMasterId, userId, projectId, userStorieId);
		} catch (UserNotAuthorizedException e) {
			return new ResponseEntity<String>("This user are not allowed to realize this operation", HttpStatus.FORBIDDEN);
		} catch (ProjectNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (UserStorieNotFound e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} return new ResponseEntity<String>("User successfully added to the us", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/us/showInterest", method = RequestMethod.POST)
	public ResponseEntity<?> showInterest(@RequestParam("userId") String userId, @RequestParam("userStorieID") String userStorieId) {
		try {
			this.userStorieService.showInterest(userId, userStorieId);
			return new ResponseEntity<String>("Interest Shown: " + userStorieId, HttpStatus.OK);
		} catch (UserStorieNotFound e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		} catch (UserNotAuthorizedException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/us/workToVerify", method = RequestMethod.PUT)
	public ResponseEntity<?> changeStateWorkToVerify(@RequestParam("userId") String userId, @RequestParam("userStorieID") String userStorieId) {
		try {
			this.userStorieService.changeStateWorkToVerify(userId, userStorieId);
			return new ResponseEntity<String>("User Storie Updated: " + userStorieId, HttpStatus.OK);
		} catch (UserStorieNotFound e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (UserNotAuthorizedException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
		} catch (UserStorieStateException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value = "/us/verityToDone", method = RequestMethod.PUT)
	public ResponseEntity<?> changeStateVerifyToDone(@RequestParam("userId") String userId, @RequestParam("userStorieID") String userStorieId) {
		try {
			this.userStorieService.changeStateVerifyToDone(userId, userStorieId);
			return new ResponseEntity<String>("User Storie Updated: " + userStorieId, HttpStatus.OK);
		} catch (UserStorieNotFound e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (UserNotAuthorizedException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
		} catch (UserStorieStateException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
}
