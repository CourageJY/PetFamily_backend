package com.pet.logistics.entity;

import java.time.Instant;

public class DetailLogisticsInfoReturn {
    public String orderNo;
    public String petID;
    public String userID;
    public double location_X;
    public double location_Y;
    public Instant logistics_time;
    public String destination;
    public String logistics_status;

    public DetailLogisticsInfoReturn(String orderNo, String petID, String userID, double location_X, double location_Y, Instant logistics_time, String destination, String logistics_status) {
        this.orderNo = orderNo;
        this.petID = petID;
        this.userID = userID;
        this.location_X = location_X;
        this.location_Y = location_Y;
        this.logistics_time = logistics_time;
        this.destination = destination;
        this.logistics_status = logistics_status;
    }

}
