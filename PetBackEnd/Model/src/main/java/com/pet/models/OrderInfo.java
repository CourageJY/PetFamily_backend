package com.pet.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "order_info")
public class OrderInfo {
    @Id
    @Column(name = "order_ID", nullable = false, length = 256)
    private String id;

    @Column(name = "title", length = 256)
    private String title;

    @Column(name = "code_url", length = 50)
    private String codeUrl;

    @Column(name = "total_fee")
    private Integer totalFee;

    @Column(name = "pet_ID", nullable = false, length = 10)
    private String petId;

    @Column(name = "order_status", length = 10)
    private String orderStatus;

    @Column(name = "create_time")
    private Instant createTime;

    @Column(name = "update_time")
    private Instant updateTime;

    @Column(name = "user_ID", length = 10)
    private String userId;

    @Column(name = "location_X")
    private Double locationX;

    @Column(name = "location_Y")
    private Double locationY;

    @Column(name = "logistics_status", length = 10)
    private String logisticsStatus;

    @Column(name = "logistics_time")
    private Instant logisticsTime;

    @Column(name = "destination", length = 60)
    private String destination;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Instant getLogisticsTime() {
        return logisticsTime;
    }

    public void setLogisticsTime(Instant logisticsTime) {
        this.logisticsTime = logisticsTime;
    }

    public String getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(String logisticsStatus) {
        this.logisticsStatus = logisticsStatus;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}