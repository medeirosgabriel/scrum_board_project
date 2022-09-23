package com.ufcg.psoftproject.models.userstories;

import java.util.*;

import com.ufcg.psoftproject.models.tasks.Task;
import com.ufcg.psoftproject.models.users.LinkedUser;
import com.ufcg.psoftproject.models.users.Listener;

public class UserStorie {

    private String id;
    private String title;
    private String description;
    private Set<LinkedUser> users;
    private Set<Listener> listeners;
    private List<Task> tasks;
    private UserStorieState state;

    public UserStorie(String title, String description) {
        this.title = title;
        this.users = new HashSet<>();
        this.listeners = new HashSet<>();
        this.description = description;
        this.id = UUID.randomUUID().toString();
        this.state = new Todo(this);
        this.tasks = new ArrayList<>();
    }

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UserStorieState getState() {
		return state;
	}

	public void setState(UserStorieState state) {
		this.state = state;
	}
	
	public void addUser(LinkedUser user) {
    	this.users.add(user);
    }
    
    public Set<LinkedUser> getUsers() {
		return users;
	}

	public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getId() {
        return this.id;
    }
    
    public void addListener(LinkedUser user) {
    	this.listeners.add(user);
    }
    
    public void notificaProductOwner() {
    	for (LinkedUser user : this.users) {
    		if (user.getUserRole().getRoleName().equals("PRODUCT_OWNER")) {
    			user.notifica(new NotifyEvent("US updated to Done"));
    		}
    	}
    }
    
    public void changeState() {
    	this.state.changeState();
    }
    
    public void changeState(NotifyEvent event) {
    	this.state.changeState();
    	for (Listener user : this.listeners) {
    		user.notifica(event);
    	}
    }
    
    public LinkedUser getLinkedUser(String userId) {
    	for (LinkedUser user : this.users) {
    		if (user.getUser().getEmail().equals(userId)) {
    			return user;
    		}
    	}
    	return null;
    }
    
    public boolean containsUser(String userId) {
    	for (LinkedUser user : this.users) {
    		if (user.getUser().getEmail().equals(userId)) {
    			return true;
    		}
    	}
    	return false;
    }

    public boolean allTasksDone() {
        for (Task task: this.tasks) {
            if (task.getState().getStatus().equals("NOT_DONE")) {
                return false;
            }
        } return true;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

}
