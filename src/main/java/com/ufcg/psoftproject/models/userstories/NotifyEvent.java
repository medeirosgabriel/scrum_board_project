package com.ufcg.psoftproject.models.userstories;

public class NotifyEvent {
	
	private String reason;
	
	public NotifyEvent(String reason) {
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
