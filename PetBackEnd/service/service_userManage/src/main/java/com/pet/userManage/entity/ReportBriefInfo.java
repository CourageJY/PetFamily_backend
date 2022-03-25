package com.pet.userManage.entity;

import com.pet.models.ReportApplication;

import java.time.Instant;

public class ReportBriefInfo {
    public String title;
    public Instant reportTime;
    public String status;
    public String applicationID;

    public ReportBriefInfo(ReportApplication reportApplication){
        title = reportApplication.getTitle();
        reportTime = reportApplication.getTime();
        status = reportApplication.getStatus();
        applicationID=reportApplication.getId();
    }
}
