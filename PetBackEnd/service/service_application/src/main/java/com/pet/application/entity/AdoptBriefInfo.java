package com.pet.application.entity;

import com.pet.models.AdoptApplication;
import com.pet.models.Pet;
import com.pet.models.User;

import java.time.Instant;

public class AdoptBriefInfo {
    public String applicationID;
    public String title;
    public Instant adoptTime;
    public Pet pet;
    public String status;
    public User user;

    public AdoptBriefInfo(AdoptApplication adoptApplication){
        applicationID=adoptApplication.getId();
        title = adoptApplication.getTitle();
        adoptTime = adoptApplication.getTime();
        pet = adoptApplication.getPet();
        status = adoptApplication.getStatus();
        user=adoptApplication.getUser();
        //敏感信息置空
        user.setSalt(null);
        user.setPassword(null);
    }
}
