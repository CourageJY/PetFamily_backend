package com.pet.logistics.entity;


public class BriefLogisticsInfoReturn {
    public String orderID;
    public String petID;
    public String userID;
    public String destination;
    public String logistics_status;

    public BriefLogisticsInfoReturn(String orderID, String petID, String userID, String destination, String logistics_status) {
        this.orderID = orderID;
        this.petID = petID;
        this.userID = userID;
        this.destination = destination;
        this.logistics_status = logistics_status;
    }
}
