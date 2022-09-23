package com.ufcg.psoftproject.services;

import java.util.Set;

import com.ufcg.psoftproject.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoftproject.dtos.UserStorieDTO;
import com.ufcg.psoftproject.exceptions.ProjectNotFoundException;
import com.ufcg.psoftproject.exceptions.UserNotAuthorizedException;
import com.ufcg.psoftproject.exceptions.UserNotFoundException;
import com.ufcg.psoftproject.exceptions.UserStorieNotFound;
import com.ufcg.psoftproject.exceptions.UserStorieStateException;
import com.ufcg.psoftproject.models.Project;
import com.ufcg.psoftproject.models.users.LinkedUser;
import com.ufcg.psoftproject.models.users.User;
import com.ufcg.psoftproject.models.userstories.NotifyEvent;
import com.ufcg.psoftproject.models.userstories.UserStorie;
import com.ufcg.psoftproject.repositories.ProjectRepository;
import com.ufcg.psoftproject.repositories.UserRepository;
import com.ufcg.psoftproject.repositories.UserStorieRepository;

@Service
public class UserStorieService {

	@Autowired
    private UserStorieRepository userStorieRepository;
	@Autowired
	private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;

    public UserStorie addUserStorie(String userId, String projectId, UserStorieDTO userStorieDTO) throws UserNotAuthorizedException, ProjectNotFoundException {
        Project project = this.projectRepository.getProjectById(projectId);
        if (project == null) throw new ProjectNotFoundException("Project not found");
        LinkedUser user = project.getUser(userId);
        if (user != null) {
        	String role = user.getUserRole().getRoleName();
        	if (role.equals("SCRUM_MASTER") || role.equals("DEVELOPER") || role.equals("RESEARCH") || role.equals("INTERN")) {
        		UserStorie userStorie = new UserStorie(userStorieDTO.getTitle(), userStorieDTO.getDescription());
        		userStorie.addUser(user);
            	userStorieRepository.addUserStorie(userStorie);
            	project.addUserStorie(userStorie);
            	this.projectRepository.updateProject(projectId, project);
            	return userStorie;

        	} else {
        		throw new UserNotAuthorizedException("You are not authorized!");
        	}
        } else {
        	throw new UserNotAuthorizedException("You are not in the project!");
        }
    }

    public void removeUserStorie(String userId, String projectId, String idUserStorie) throws UserStorieNotFound, ProjectNotFoundException, UserNotAuthorizedException {
        UserStorie Us = userStorieRepository.getUs(idUserStorie);
        if (!(userStorieRepository.containsUS(Us))) throw new UserStorieNotFound("User Storie not found");
        Project project = this.projectRepository.getProjectById(projectId);
        if (project == null) throw new ProjectNotFoundException("Project not found");
        LinkedUser user = project.getUser(userId);
        if (user != null) {
	        userStorieRepository.deleteUs(Us);
	        project.getUserStories().remove(Us);
	        this.projectRepository.updateProject(projectId, project);
        } else {
        	throw new UserNotAuthorizedException("You are not in the project!");
        }
    }

    public void updateUserStorie(String userId, String projectId, String usId, UserStorieDTO userStorie) throws UserStorieNotFound, ProjectNotFoundException, UserNotAuthorizedException {
    	UserStorie Us = userStorieRepository.getUs(usId);
        if (!(userStorieRepository.containsUS(Us))) throw new UserStorieNotFound("User Storie not found");
        Project project = this.projectRepository.getProjectById(projectId);
        if (project == null) throw new ProjectNotFoundException("Project not found");
        LinkedUser user = project.getUser(userId);
        if (user != null) {
	        project.getUserStories().remove(Us);
	        Us.setTitle(userStorie.getTitle());
	        Us.setDescription(userStorie.getDescription());
	        project.addUserStorie(Us);
	        userStorieRepository.updateUs(usId, Us);
        } else {
        	throw new UserNotAuthorizedException("You are not in the project!");
        }
    }
    
    public UserStorie getUserStorie(String userId, String projectId, String idUs) throws UserStorieNotFound, UserNotAuthorizedException, ProjectNotFoundException {
    	UserStorie Us = userStorieRepository.getUs(idUs);
        if (!(userStorieRepository.containsUS(Us))) throw new UserStorieNotFound("User Storie not found");
        Project project = this.projectRepository.getProjectById(projectId);
        if (project == null) throw new ProjectNotFoundException("Project not found");
        LinkedUser user = project.getUser(userId);
        if (user != null) {
        	return Us;
        } else {
        	throw new UserNotAuthorizedException("You are not in the project!");
        }
    }
    
    public Set<UserStorie> getAllUserStories(String userId, String projectId) throws UserStorieNotFound, UserNotAuthorizedException, ProjectNotFoundException {
    	Project project = this.projectRepository.getProjectById(projectId);
        if (project == null) throw new ProjectNotFoundException("Project not found");
        LinkedUser user = project.getUser(userId);
        if (user != null) {
        	return project.getUserStories();
        } else {
        	throw new UserNotAuthorizedException("You are not in the project!");
        }
    }

    public void linkUserToUs(String userId, String projectId,  String userStorieId) throws UserStorieNotFound,
            ProjectNotFoundException, UserNotAuthorizedException {

        UserStorie us = this.userStorieRepository.getUs(userStorieId);
        if (us == null) throw new UserStorieNotFound("User storie not found");

        Project project = this.projectRepository.getProjectById(projectId);
        if (project == null) throw new ProjectNotFoundException("Project not found");

        LinkedUser user = project.getUser(userId);
        if (user == null)
        throw new UserNotAuthorizedException("User not authorized into this project");

        if (user.getUserRole().getRoleName().equals("DEVELOPER") 
        		|| user.getUserRole().getRoleName().equals("INTERN") 
        		|| user.getUserRole().getRoleName().equals("RESEARCH")) {
        	
            this.userStorieRepository.linkUserToUs(user, userStorieId);

            if (us.getState().getName().equals("TODO")) {
                us.changeState();
            }
            
            this.userStorieRepository.updateUs(us.getId(), us);

        } else {
            throw new UserNotAuthorizedException("User does not have a required role");
        }
    }

    public void masterLinkUserToUs (String masterId, String userId, String projectId, String userStorieId) throws UserStorieNotFound,
            ProjectNotFoundException, UserNotAuthorizedException {

        User user = this.userRepository.getUserByEmail(masterId); //Currently we don't check if a Scrum Master is linked to a project, maybe we should do it later ?
        if (!(user.isScrumMaster())) throw new UserNotAuthorizedException("You should be a Scrum Master to realize this operation");

        this.linkUserToUs(userId, projectId, userStorieId);

    }

	public void changeStateWorkToVerify(String userId, String userStorieId) throws UserStorieNotFound, UserNotAuthorizedException, 
			UserStorieStateException {
	    UserStorie us = this.userStorieRepository.getUs(userStorieId);
        if (us == null) throw new UserStorieNotFound("User storie not found");
        if (!us.containsUser(userId)) throw new UserNotAuthorizedException("User not Authorized");
        if (!us.getState().getName().equals("WORK_IN_PROGRESS")) throw new UserStorieStateException("State Innapropriate");
        us.changeState(new NotifyEvent("US updated Work To Verify"));
        this.userStorieRepository.updateUs(userStorieId, us);
	}

	public void changeStateVerifyToDone(String userId, String userStorieId) throws UserStorieNotFound, UserNotAuthorizedException, 
			UserStorieStateException {
		UserStorie us = this.userStorieRepository.getUs(userStorieId);
        if (us == null) throw new UserStorieNotFound("User storie not found");
        if (!us.containsUser(userId)) throw new UserNotAuthorizedException("User not Authorized");
        if (!us.getState().getName().equals("TO_VERIFY")) throw new UserStorieStateException("State Innapropriate");        
        us.notificaProductOwner();
        us.changeState(new NotifyEvent("US updated Verify To Done"));
        this.userStorieRepository.updateUs(userStorieId, us);
	}

	public void showInterest(String userId, String userStorieId) throws UserStorieNotFound, UserNotAuthorizedException, UserNotFoundException {
		UserStorie us = this.userStorieRepository.getUs(userStorieId);
        if (us == null) throw new UserStorieNotFound("User storie not found");
        LinkedUser linkedUser = us.getLinkedUser(userId);
        if (!us.containsUser(userId)) throw new UserNotAuthorizedException("User not Authorized");
        us.addListener(linkedUser);
        this.userStorieRepository.updateUs(userStorieId, us);
	}
}
