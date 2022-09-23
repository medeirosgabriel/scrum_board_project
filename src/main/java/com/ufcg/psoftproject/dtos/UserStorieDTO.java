package com.ufcg.psoftproject.dtos;

public class UserStorieDTO {
	
    private String title;
    private String description;

    public UserStorieDTO(String title, String description) {
    	this.title = title;
        this.description = description;
	}

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }
}
