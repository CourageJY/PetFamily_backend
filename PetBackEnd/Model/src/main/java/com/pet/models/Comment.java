package com.pet.models;

import javax.persistence.*;
import java.time.Instant;

@Table(name = "comment", indexes = {
        @Index(name = "comment_id_pk", columnList = "comment_id", unique = true)
})
@Entity
public class Comment {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "time")
    private Instant time;

    @Column(name = "content", length = 200)
    private String content;

    @Column(name = "status", length = 20)
    private String status;

    @Id
    @Column(name = "comment_id", length = 16)
    private String commentId;

    @Column(name = "post_id", length = 16)
    private String postId;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}