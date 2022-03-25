package com.pet.application.entity;


import java.time.Instant;

public class Update {
    final private long plusTime =28800L;//8小时

    public String applicationID;

    public String title;

    public Instant time;

    public String status;

    public  String phoneNum;

    public void setUpdateTime(){
        time=java.time.Instant.now().plusSeconds(this.plusTime);//设置更新时间
    }

    public long getPlusTime() {
        return plusTime;
    }

    public String getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(String applicationID) {
        this.applicationID = applicationID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
