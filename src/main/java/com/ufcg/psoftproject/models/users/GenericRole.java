package com.ufcg.psoftproject.models.users;

import java.util.*;

import com.ufcg.psoftproject.models.userstories.UserStorie;

public abstract class GenericRole {

    public final String generateDescription(Set<UserStorie> userStories, String user) {
        String percentage = getPercentage(userStories, user);
        String percentageByStage = getPercentageByStage(userStories);
        String percentageByStageAndUser = getPercentageByStageAndUser(userStories, user);

        return percentage +  percentageByStage + percentageByStageAndUser;

    }

    protected String getPercentage(Set<UserStorie> userStoriesList, String userId) {
        int count = 0;
        float percentage = 0;
        List<String> result = new ArrayList<>();

        for (UserStorie us: userStoriesList) {
            if (us.containsUser(userId)) {
                count++;
            }  percentage = ((float) count) / userStoriesList.size();
        }

        return "The user: " + userId + " is currently working in: " + count + " User stories and that is " +
                (percentage * 100) + "% of total Us's\n";
    }

    protected String getPercentageByStage(Set<UserStorie> userStories) {
        Map<String, Integer> stagesMap = new HashMap<>();

        stagesMap.put("TODO", 0);
        stagesMap.put("WORK_IN_PROGRESS", 0);
        stagesMap.put("TO_VERIFY", 0);
        stagesMap.put("DONE", 0);


        for (UserStorie us: userStories) {
            stagesMap.put(us.getState().getName(), stagesMap.get(us.getState().getName())+1);
        }


        return "There you can check the number of US sorted by status: \n" +
                "TODO, total: " + stagesMap.get("TODO") + ", " + ((float) stagesMap.get("TODO") / userStories.size()) * 100 + "%\n"
                + "WORK IN PROGRESS, total: " + stagesMap.get("WORK_IN_PROGRESS") + ", " + ((float) stagesMap.get("WORK_IN_PROGRESS") / userStories.size()) * 100 + "%\n"
                + "TO VERIFY, total: " + stagesMap.get("TO_VERIFY") + ", " + (((float) stagesMap.get("TO_VERIFY")) / userStories.size()) * 100 + "%\n"
                + "DONE, total: " + stagesMap.get("DONE") + ", " + (((float) stagesMap.get("DONE")) / userStories.size()) * 100 + "%\n";
    }

    protected String getPercentageByStageAndUser(Set<UserStorie> userStories, String userId) {
        Map<String, Integer> stagesMap = new HashMap<>();

        stagesMap.put("TODO", 0);
        stagesMap.put("WORK_IN_PROGRESS", 0);
        stagesMap.put("TO_VERIFY", 0);
        stagesMap.put("DONE", 0);


        for (UserStorie us: userStories) {
            if (us.containsUser(userId)) {
                stagesMap.put(us.getState().getName(), stagesMap.get(us.getState().getName()) + 1);
            }
        }

        return "There you can check the number of US sorted by status: \n" +
                "TODO, total: " + stagesMap.get("TODO") + ", " + ((float) stagesMap.get("TODO") / userStories.size()) * 100 + "%\n"
                + "WORK IN PROGRESS, total: " + stagesMap.get("WORK_IN_PROGRESS") + ", " + ((float) stagesMap.get("WORK_IN_PROGRESS") / userStories.size()) * 100 + "%\n"
                + "TO VERIFY, total: " + stagesMap.get("TO_VERIFY") + ", " + (((float) stagesMap.get("TO_VERIFY")) / userStories.size()) * 100 + "%\n"
                + "DONE, total: " + stagesMap.get("DONE") + ", " + (((float) stagesMap.get("DONE")) / userStories.size()) * 100 + "%\n";

    }
}