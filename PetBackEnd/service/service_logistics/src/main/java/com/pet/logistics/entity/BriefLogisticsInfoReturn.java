package com.pet.logistics.entity;


public class BriefLogisticsInfoReturn {
    public String orderNo;
    public String destination;
    public String logistics_status;

    public BriefLogisticsInfoReturn(String orderNo,  String destination, String logistics_status) {
        this.orderNo = orderNo;
        this.destination = destination;
        this.logistics_status = logistics_status;
    }
}
