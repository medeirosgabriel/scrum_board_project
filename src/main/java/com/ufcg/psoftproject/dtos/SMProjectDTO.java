package com.ufcg.psoftproject.dtos;

public class SMProjectDTO {
	
	private String UserID;
	private ProjectDTO projectDTO;
	
	public SMProjectDTO(String userID, ProjectDTO projectDTO) {
		super();
		UserID = userID;
		this.projectDTO = projectDTO;
	}

	public String getUserID() {
		return UserID;
	}
	
	public void setUserID(String userID) {
		UserID = userID;
	}
	
	public ProjectDTO getProjectDTO() {
		return projectDTO;
	}
	
	public void setProjectDTO(ProjectDTO projectDTO) {
		this.projectDTO = projectDTO;
	}
}
