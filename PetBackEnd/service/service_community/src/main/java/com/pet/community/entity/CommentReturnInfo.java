package com.pet.community.entity;

import com.pet.models.Comment;
import org.joda.time.DateTime;

import java.time.Instant;

public class CommentReturnInfo {
    public String userId;
    public String userName;
    public String userAvatar;
    public Instant time;
    public String content;
    public String commentId;
    public String postId;

    public CommentReturnInfo(Comment comment){
        this.commentId=comment.getCommentId();
        this.content=comment.getContent();
        this.postId=comment.getPostId();
        this.userId=comment.getUser().getId();
        this.userName=comment.getUser().getName();
        this.userAvatar=comment.getUser().getAvatar();
        this.time=comment.getTime();
    }

    public CommentReturnInfo(){}

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

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
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

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }
}
