package com.ufcg.psoftproject.dtos;

public class ProjectDTO {
	
	private String name;
	private String description;
	private String institution;
	
	public ProjectDTO(String name, String description, String institution) {
		super();
		this.name = name;
		this.description = description;
		this.institution = institution;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getInstitution() {
		return institution;
	}
	
	public void setInstitution(String institution) {
		this.institution = institution;
	}

}
