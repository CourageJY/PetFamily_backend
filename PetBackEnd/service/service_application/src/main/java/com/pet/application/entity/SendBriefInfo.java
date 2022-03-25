package com.pet.application.entity;

import com.pet.models.SendApplication;
import com.pet.models.User;

import java.time.Instant;

public class SendBriefInfo {
    public String applicationID;
    public String title;
    public Instant time;
    public String status;
    public User user;

    public SendBriefInfo(SendApplication sendApplication){
        applicationID=sendApplication.getId();
        title = sendApplication.getTitle();
        time = sendApplication.getTime();
        status = sendApplication.getStatus();
        user=sendApplication.getUser();
        //敏感信息置空
        user.setSalt(null);
        user.setPassword(null);
    }
}
