package com.pet.application.entity;

import com.pet.models.FindApplication;
import com.pet.models.Pet;
import com.pet.models.User;

import java.time.Instant;

public class FindBriefInfo {
    public String applicationID;
    public String title;
    public Instant applyTime;
    public String status;
    public User user;
    public Pet pet;

    public FindBriefInfo(FindApplication findApplication){
        applicationID=findApplication.getId();
        title = findApplication.getTitle();
        applyTime = findApplication.getTime();
        status = findApplication.getStatus();
        user=findApplication.getUser();
        pet=findApplication.getPet();
        //敏感信息置空
        user.setSalt(null);
        user.setPassword(null);
    }

}
