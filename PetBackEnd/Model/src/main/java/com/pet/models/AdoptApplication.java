package com.pet.models;

import javax.persistence.*;
import java.time.Instant;

@Table(name = "adopt_application", indexes = {
        @Index(name = "user_ID", columnList = "user_ID"),
        @Index(name = "pet_ID", columnList = "pet_ID")
})
@Entity
public class AdoptApplication {
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

    @Column(name = "economic_condition", length = 16)
    private String economicCondition;

    @Column(name = "reason", length = 1000)
    private String reason;

    @Column(name = "status", length = 16)
    private String status;

    @Column(name = "area", length = 60)
    private String area;

    @Column(name = "phone_num", length = 13)
    private String phoneNum;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getEconomicCondition() {
        return economicCondition;
    }

    public void setEconomicCondition(String economicCondition) {
        this.economicCondition = economicCondition;
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