package com.pet.application.entity;

import com.pet.models.ReportApplication;
import com.pet.models.User;

import java.time.Instant;

public class ReportBriefInfo {
    public String applicationID;
    public String title;
    public Instant reportTime;
    public String status;
    public User user;

    public ReportBriefInfo(ReportApplication reportApplication){
        applicationID=reportApplication.getId();
        title = reportApplication.getTitle();
        reportTime = reportApplication.getTime();
        status = reportApplication.getStatus();
        user=reportApplication.getUser();
        //敏感信息置空
        user.setSalt(null);
        user.setPassword(null);
    }
}
