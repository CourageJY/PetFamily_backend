package com.pet.community.entity;

import com.pet.models.Comment;
import com.pet.models.ReportPost;
import org.joda.time.DateTime;

import java.time.Instant;

public class ReportPostReturnInfo {
    public String userId;
    public String userName;
    public String userAvatar;
    public Instant time;
    public String content;
    public String reason;
    public String reportId;
    public String postId;

    public ReportPostReturnInfo(ReportPost reportPost){
        this.reportId=reportPost.getId();
        this.content=reportPost.getContent();
        this.postId=reportPost.getPostId();
        this.userId=reportPost.getUser().getId();
        this.userName=reportPost.getUser().getName();
        this.userAvatar=reportPost.getUser().getAvatar();
        this.time=reportPost.getTime();
        this.reason=reportPost.getReason();
    }

    public ReportPostReturnInfo(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
