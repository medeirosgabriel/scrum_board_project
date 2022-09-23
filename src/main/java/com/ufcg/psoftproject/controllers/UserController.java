package com.ufcg.psoftproject.controllers;

import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.ufcg.psoftproject.exceptions.UserNotFoundException;
import com.ufcg.psoftproject.models.users.User;
import com.ufcg.psoftproject.models.users.UserRole;
import com.ufcg.psoftproject.services.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		User newUser = this.userService.createUser(user);
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/user/{email}", method = RequestMethod.GET)
	public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email) {
		User user;
		try {
			user = this.userService.getUserByEmail(email);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<?> getUserAllUsers() {
		List<User> users = this.userService.getAllUsers();
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/user/{email}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeUser(@PathVariable("email") String email) {
		try {
			this.userService.removeUser(email);
			return new ResponseEntity<String>("User removed: " + email, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/user/", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		try {
			this.userService.updateUser(user);
			return new ResponseEntity<String>("User updated: " + user.getEmail(), HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/user/roles", method = RequestMethod.GET)
	public ResponseEntity<?> getRoles(@RequestParam("idScrumMaster") String idSM) {
		if (this.userService.verifyIsSM(idSM)) {
			List<UserRole> roles = this.userService.getRoles().values().stream().toList();
			return new ResponseEntity<List<UserRole>>(roles, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("You are not a Scrum Master", HttpStatus.OK);
		}
	}
}
