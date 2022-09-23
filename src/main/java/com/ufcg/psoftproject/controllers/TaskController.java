package com.ufcg.psoftproject.controllers;

import com.ufcg.psoftproject.dtos.TaskDTO;
import com.ufcg.psoftproject.exceptions.*;
import com.ufcg.psoftproject.models.Project;
import com.ufcg.psoftproject.models.tasks.Task;
import com.ufcg.psoftproject.models.users.LinkedUser;
import com.ufcg.psoftproject.models.userstories.UserStorie;
import com.ufcg.psoftproject.repositories.ProjectRepository;
import com.ufcg.psoftproject.repositories.UserRepository;
import com.ufcg.psoftproject.services.TaskService;
import com.ufcg.psoftproject.services.UserService;
import com.ufcg.psoftproject.services.UserStorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/task", method = RequestMethod.POST)
    public ResponseEntity<?> createTask(@RequestParam("userId") String userId, @RequestParam("projectId") String projectId,
                                        @RequestParam("userStorieId") String userStorieId, @RequestBody TaskDTO taskDTO) {
        try {
            this.taskService.addTask(userId, projectId, userStorieId, taskDTO);
        } catch (UserStorieNotFound e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ProjectNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UserNotAuthorizedException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
        } return new ResponseEntity<String>("Successfully created task", HttpStatus.OK);
    }

    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeTask(@RequestParam("userId") String userId, @RequestParam("projectId") String projectId,
                                        @RequestParam("userStorieId") String userStorieId, @PathVariable("taskId") String taskId) {
        try {
            this.taskService.removeTask(taskId, userId, projectId, userStorieId);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ProjectNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UserNotAuthorizedException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (UserStorieNotFound e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } return new ResponseEntity<String>("Task removed", HttpStatus.OK);
    }

    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.GET)
    public ResponseEntity<?> getTask(@RequestParam("userId") String userId, @RequestParam("projectId") String projectId,
                                     @RequestParam("userStorieId") String userStorieId, @PathVariable("taskId") String taskId) {
        Task task;
        try {
            task = this.taskService.getTask(taskId, userId, projectId, userStorieId);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ProjectNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UserStorieNotFound e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Task>(task, HttpStatus.OK);
    }

    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateTask(@RequestParam("userId") String userId, @RequestParam("ProjectId") String projectId,
                                        @RequestParam("userStorieId") String userStorieId, @PathVariable("taskId") String taskId, @RequestBody TaskDTO taskDTO) {
        String id;
        try {
            id = this.taskService.updateTask(userId, projectId, userStorieId, taskId, taskDTO);
        } catch (TaskNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NullPointerException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ProjectNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UserNotAuthorizedException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (UserStorieNotFound e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>("Task with the following id: " + id + " was updated", HttpStatus.OK);
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public ResponseEntity<?> listTasks(@RequestParam("userId") String userId, @RequestParam("projectId") String projectId,
    @RequestParam("userStorieId") String userStorieId) {
        List<Task> tasks;
        try {
            tasks = this.taskService.getAllTasks(userId, projectId, userStorieId);
        } catch (ProjectNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UserStorieNotFound e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }


    @RequestMapping(value = "/task/state", method = RequestMethod.PUT)
    public ResponseEntity<?> changeTaskState(@RequestParam("userId") String userId, @RequestParam("projectId") String projectId,
                                             @RequestParam("userStorieId") String userStorieId, @RequestParam("TaskID") String idTask) throws TaskNotFoundException {
        try {
            this.taskService.changeTaskState(userId, projectId, userStorieId, idTask);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ProjectNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UserStorieNotFound e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UserNotAuthorizedException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
        } return new ResponseEntity<String>("Task successfully updated", HttpStatus.OK);
    }


}
