package com.pet.userManage.entity;

import com.pet.models.FindApplication;

import java.time.Instant;

public class FindBriefInfo {
    public String title;
    public Instant applyTime;
    public String status;
    public String applicationID;
    public String petID;

    public FindBriefInfo(FindApplication findApplication){
        title = findApplication.getTitle();
        applyTime = findApplication.getTime();
        status = findApplication.getStatus();
        applicationID=findApplication.getId();
        petID=findApplication.getPet().getId();
    }

}
