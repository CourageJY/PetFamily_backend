package com.pet.models;

import javax.persistence.*;
import java.time.Instant;

@Table(name = "report_post")
@Entity
public class ReportPost {
    @Id
    @Column(name = "id", nullable = false, length = 16)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "reason", length = 50)
    private String reason;

    @Column(name = "content", length = 200)
    private String content;

    @Column(name = "post_id", length = 16)
    private String postId;

    @Column(name = "time")
    private Instant time;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }
}