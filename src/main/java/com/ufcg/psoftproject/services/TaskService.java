package com.ufcg.psoftproject.services;

import com.ufcg.psoftproject.dtos.TaskDTO;
import com.ufcg.psoftproject.exceptions.*;
import com.ufcg.psoftproject.models.Project;
import com.ufcg.psoftproject.models.tasks.Task;
import com.ufcg.psoftproject.models.users.LinkedUser;
import com.ufcg.psoftproject.models.userstories.UserStorie;
import com.ufcg.psoftproject.repositories.ProjectRepository;
import com.ufcg.psoftproject.repositories.TaskRepository;
import com.ufcg.psoftproject.repositories.UserRepository;
import com.ufcg.psoftproject.repositories.UserStorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserStorieRepository userStorieRepository;

    @Autowired
    private UserRepository userRepository;

    public void addTask(String userId, String projectId,  String userStorieId, TaskDTO taskDTO) throws UserStorieNotFound,
            ProjectNotFoundException, UserNotAuthorizedException {

        Project project = this.projectRepository.getProjectById(projectId);
        if (project == null) throw new ProjectNotFoundException("Project not found");

        LinkedUser user = project.getUser(userId);
        if (user == null) throw new UserNotAuthorizedException("This user is not found into this project");

        UserStorie userStorie = this.userStorieRepository.getUs(userStorieId);
        if (userStorie == null) throw new UserStorieNotFound("User storie not found");

        if (userStorie.containsUser(user.getUser().getEmail()) || user.getUserRole().equals("SCRUM_MASTER")) {
            Task task = new Task(taskDTO.getTitle(), taskDTO.getDescription(), userStorie);
            this.taskRepository.addTask(task);
            UserStorie us = this.userStorieRepository.getUs(userStorieId);
            us.addTask(task);

        } else throw new UserNotAuthorizedException("This user is not allowed to modify this User Storie");

    }

    public void removeTask(String idTask, String userId, String projectId, String userStorieId)
            throws TaskNotFoundException, ProjectNotFoundException, UserNotFoundException, UserStorieNotFound,
            UserNotAuthorizedException {

        Project project = this.projectRepository.getProjectById(projectId);
        if (project == null) throw new ProjectNotFoundException("Project not found");

        LinkedUser user = project.getUser(userId);
        if (user == null) throw new UserNotFoundException("User not found");

        UserStorie userStorie = this.userStorieRepository.getUs(userStorieId);
        if (userStorie == null) throw new UserStorieNotFound("User storie not found");

        if (userStorie.containsUser(user.getUser().getEmail()) || user.getUserRole().equals("SCRUM_MASTER")) {
            Task task = this.taskRepository.getTask(idTask);
            if (task == null) throw new TaskNotFoundException("Task not found");
            this.taskRepository.deleteTask(idTask);
        } else throw new UserNotAuthorizedException("This user is not allowed to realize this operation");

    }

    public String updateTask(String userId, String projectId, String userStorieId, String taskId, TaskDTO taskDto) throws TaskNotFoundException, NullPointerException, ProjectNotFoundException, UserNotFoundException, UserStorieNotFound, UserNotAuthorizedException {

        Project project = this.projectRepository.getProjectById(projectId);
        if (project == null) throw new ProjectNotFoundException("Project not found");

        LinkedUser user = project.getUser(userId);
        if (user == null) throw new UserNotFoundException("User not found");

        UserStorie userStorie = this.userStorieRepository.getUs(userStorieId);
        if (userStorie == null) throw new UserStorieNotFound("User storie not found");

        if (userStorie.containsUser(user.getUser().getEmail()) || user.getUserRole().equals("SCRUM_MASTER")) {
            Task task = this.taskRepository.getTask(taskId);
            if (task == null) throw new TaskNotFoundException("Task not found");

            if (taskDto.getTitle().equals("") || taskDto.getDescription().equals(""))
                throw new NullPointerException("Null paramaters" +
                        "are not accepted");

            task.setTitle(taskDto.getTitle());
            task.setDescription(taskDto.getDescription());

            return task.getId();
        } else throw new UserNotAuthorizedException("User not allowed to realize this operation");
    }

    public Task getTask(String idTask, String userId, String projectId, String userStorieId) throws TaskNotFoundException, ProjectNotFoundException,
            UserNotFoundException, UserStorieNotFound {

        Project project = this.projectRepository.getProjectById(projectId);
        if (project == null) throw new ProjectNotFoundException("Project not found");

        LinkedUser user = project.getUser(userId);
        if (user == null) throw new UserNotFoundException("User not found");

        UserStorie userStorie = this.userStorieRepository.getUs(userStorieId);
        if (userStorie == null) throw new UserStorieNotFound("User storie not found");

        Task task = this.taskRepository.getTask(idTask);
        if (task == null) throw new TaskNotFoundException("Task not found");

        return task;
    }

    public List<Task> getAllTasks(String userId, String projectId, String userStorieId) throws ProjectNotFoundException,
            UserNotFoundException, UserStorieNotFound {

        Project project = this.projectRepository.getProjectById(projectId);
        if (project == null) throw new ProjectNotFoundException("Project not found");

        LinkedUser user = project.getUser(userId);
        if (user == null) throw new UserNotFoundException("User not found");

        UserStorie userStorie = this.userStorieRepository.getUs(userStorieId);
        if (userStorie == null) throw new UserStorieNotFound("User storie not found");

        return this.taskRepository.getAllTasks();
    }

    public void changeTaskState(String userId, String projectId, String userStorieId, String idTask) throws TaskNotFoundException, ProjectNotFoundException,
            UserNotFoundException, UserStorieNotFound, UserNotAuthorizedException {
        Project project = this.projectRepository.getProjectById(projectId);
        if (project == null) throw new ProjectNotFoundException("Project not found");

        LinkedUser user = project.getUser(userId);
        if (user == null) throw new UserNotFoundException("User not found");

        UserStorie userStorie = this.userStorieRepository.getUs(userStorieId);
        if (userStorie == null) throw new UserStorieNotFound("User storie not found");

        Task task = this.taskRepository.getTask(idTask);
        if (task == null) throw new TaskNotFoundException("Task not found");

        if (userStorie.containsUser(user.getUser().getEmail()) || user.getUserRole().equals("SCRUM_MASTER")) {
            task.updateState();
            if (userStorie.getState().getName().equals("WORK_IN_PROGRESS") && userStorie.allTasksDone()) {
                this.userStorieRepository.changeUsState(userStorieId);
            }
        } else throw new UserNotAuthorizedException("This user is not allowed to realize this operation");

    }


}
