package com.pet.models;

import javax.persistence.*;
import java.time.Instant;

@Table(name = "common_post", indexes = {
        @Index(name = "user_ID", columnList = "user_ID")
})
@Entity
public class CommonPost {
    @Id
    @Column(name = "ID", nullable = false, length = 10)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_ID")
    private User user;

    @Column(name = "time")
    private Instant time;

    @Column(name = "thumbs_up")
    private Integer thumbsUp;

    @Column(name = "content", length = 400)
    private String content;

    @Column(name = "photo_one", length = 30)
    private String photoOne;

    @Column(name = "photo_two", length = 30)
    private String photoTwo;

    @Column(name = "photo_three", length = 30)
    private String photoThree;

    @Column(name = "status", length = 6)
    private String status;

    @Column(name = "title", length = 50)
    private String title;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhotoThree() {
        return photoThree;
    }

    public void setPhotoThree(String photoThree) {
        this.photoThree = photoThree;
    }

    public String getPhotoTwo() {
        return photoTwo;
    }

    public void setPhotoTwo(String photoTwo) {
        this.photoTwo = photoTwo;
    }

    public String getPhotoOne() {
        return photoOne;
    }

    public void setPhotoOne(String photoOne) {
        this.photoOne = photoOne;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(Integer thumbsUp) {
        this.thumbsUp = thumbsUp;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle(){return title;}

    public void setTitle(String title){this.title=title;}
}