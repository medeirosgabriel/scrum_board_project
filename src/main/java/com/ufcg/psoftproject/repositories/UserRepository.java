package com.ufcg.psoftproject.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ufcg.psoftproject.models.users.User;

@Repository
public class UserRepository {
	
	private Map<String, User> users = new HashMap<String, User>();
	
	public boolean containsUser(String id) {
		return this.users.containsKey(id);
	}
	
	public User getUserByEmail(String email) {
		return this.users.get(email);
	}
	
	public List<User> getAllUsers() {
		return this.users.values().stream().toList();
	}
	
	public void createUser(User user) {
		this.users.put(user.getEmail(), user);
	}
	
	public void removeUser(String email) {
		this.users.remove(email);
	}
	
	public void updateUser(User user) {
		this.users.put(user.getEmail(), user);
	}
}
