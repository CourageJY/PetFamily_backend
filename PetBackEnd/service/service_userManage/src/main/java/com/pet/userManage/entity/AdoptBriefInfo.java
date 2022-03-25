package com.pet.userManage.entity;

import com.pet.models.AdoptApplication;
import com.pet.models.Pet;

import java.time.Instant;

public class AdoptBriefInfo {
    public String title;
    public Instant adoptTime;
    public String petID;
    public String status;
    public String applicationID;

    public AdoptBriefInfo(AdoptApplication adoptApplication){
        title = adoptApplication.getTitle();
        adoptTime = adoptApplication.getTime();
        petID = adoptApplication.getPet().getId();
        status = adoptApplication.getStatus();
        applicationID=adoptApplication.getId();
    }
}
