package com.pet.pay.entity;

import lombok.Data;

@Data
public class OrderGenerate{
    private String petId;//前端提交petId
    private String userId;//前端提交用户Id
    private String destination;//前端提交订单目的地
}
