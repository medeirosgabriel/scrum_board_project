package com.ufcg.psoftproject.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ufcg.psoftproject.models.*;
import com.ufcg.psoftproject.models.users.Developer;
import com.ufcg.psoftproject.models.users.Intern;
import com.ufcg.psoftproject.models.users.ProductOwner;
import com.ufcg.psoftproject.models.users.Research;
import com.ufcg.psoftproject.models.users.ScrumMaster;
import com.ufcg.psoftproject.models.users.User;
import com.ufcg.psoftproject.models.users.UserRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoftproject.exceptions.UserNotFoundException;
import com.ufcg.psoftproject.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public boolean verifyIsSM(String email) {
		User user = this.userRepository.getUserByEmail(email);
		return user != null && user.isScrumMaster();
	}
	
	public User getUserByEmail(String email) throws UserNotFoundException {
		if (!this.userRepository.containsUser(email)) {
			throw new UserNotFoundException("User Not Found");
		}
		return this.userRepository.getUserByEmail(email);
	}
	
	public List<User> getAllUsers() {
		return this.userRepository.getAllUsers();
	}
	
	public User createUser(User user) {
		this.userRepository.createUser(user);
		return user;
	}
	
	public void removeUser(String email) throws UserNotFoundException {
		if (!this.userRepository.containsUser(email)) {
			throw new UserNotFoundException("User Not Found");
		}
		this.userRepository.removeUser(email);
	}
	
	public User updateUser(User user) throws UserNotFoundException {
		if (!this.userRepository.containsUser(user.getEmail())) {
			throw new UserNotFoundException("User Not Found");
		}
		this.userRepository.updateUser(user);
		return user;
	}

	public Map<String, UserRole> getRoles() {
		Map<String, UserRole> userRoles = new HashMap<>();
		userRoles.put("DEVELOPER", new Developer());
		userRoles.put("INTERN", new Intern());
		userRoles.put("RESEARCH", new Research());
		userRoles.put("SCRUM_MASTER", new ScrumMaster());
		userRoles.put("PRODUCT_OWNER", new ProductOwner());
		return userRoles;
	}
}
