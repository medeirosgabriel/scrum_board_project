package com.ufcg.psoftproject.repositories;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ufcg.psoftproject.models.users.LinkedUser;
import com.ufcg.psoftproject.models.users.User;
import com.ufcg.psoftproject.models.userstories.UserStorie;

@Repository
public class UserStorieRepository {
    private Map<String, UserStorie> userStories = new HashMap<>();

    public UserStorie getUs(String idUS) {
        return userStories.get(idUS);
    }

    public void addUserStorie(UserStorie userStorie) {
        userStories.put(userStorie.getId(), userStorie);
    }

    public Collection<UserStorie> getAllUs () {
        return userStories.values();
    }

    public void deleteUs(UserStorie userStorie) {
        userStories.remove(userStorie);
    }

    public void updateUs(String idUs, UserStorie userStorie) {
        userStories.replace(idUs, userStorie);
    }

    public boolean containsUS(UserStorie userStorie) {
       return userStories.containsValue(userStorie);
    }

    public void linkUserToUs(LinkedUser user, String idUs) {
        UserStorie us = this.getUs(idUs);
        us.addUser(user);
        userStories.replace(idUs, us);
    }

    public void changeUsState(String userStorieId) {
        UserStorie us = this.userStories.get(userStorieId);
        us.changeState();
        userStories.replace(userStorieId, us);
    }
}
