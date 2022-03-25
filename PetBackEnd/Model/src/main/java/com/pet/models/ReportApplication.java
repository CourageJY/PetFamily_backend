package com.pet.models;

import javax.persistence.*;
import java.time.Instant;

@Table(name = "report_application", indexes = {
        @Index(name = "user_ID", columnList = "user_ID"),
        @Index(name = "pet_ID", columnList = "pet_ID")
})
@Entity
public class ReportApplication {
    @Id
    @Column(name = "application_ID", nullable = false, length = 10)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "pet_ID")
    private Pet pet;

    @Column(name = "title", length = 20)
    private String title;

    @Column(name = "time")
    private Instant time;

    @Column(name = "location_X")
    private Double locationX;

    @Column(name = "location_Y")
    private Double locationY;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "photo_one", length = 100)
    private String photoOne;

    @Column(name = "photo_two", length = 100)
    private String photoTwo;

    @Column(name = "status", length = 16)
    private String status;

    @Column(name = "phone_num", length = 13)
    private String phoneNum;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLocationY() {
        return locationY;
    }

    public void setLocationY(Double locationY) {
        this.locationY = locationY;
    }

    public Double getLocationX() {
        return locationX;
    }

    public void setLocationX(Double locationX) {
        this.locationX = locationX;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
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
}