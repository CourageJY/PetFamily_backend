package com.pet.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "logistics_info")
public class LogisticsInfo {
    @Id
    @Column(name = "order_no", nullable = false, length = 50)
    private String id;

    @Column(name = "location_X")
    private Double locationX;

    @Column(name = "location_Y")
    private Double locationY;

    @Column(name = "logistics_status", length = 20)
    private String logisticsStatus;

    @Column(name = "destination", length = 50)
    private String destination;

    @Column(name = "logistics_time")
    private Instant logisticsTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLocationX() {
        return locationX;
    }

    public void setLocationX(Double locationX) {
        this.locationX = locationX;
    }

    public Double getLocationY() {
        return locationY;
    }

    public void setLocationY(Double locationY) {
        this.locationY = locationY;
    }

    public String getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(String logisticsStatus) {
        this.logisticsStatus = logisticsStatus;
    }

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

}