package com.ufcg.psoftproject.models.users;

import com.ufcg.psoftproject.models.userstories.NotifyEvent;

public interface Listener {
	
	public void notifica(NotifyEvent event);

}
