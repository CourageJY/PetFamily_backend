package com.pet.userManage.service;

import com.pet.userManage.repository.OrderInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderInfoService {
    @Autowired
    OrderInfoRepository orderInfoRepository;

    public Integer getOrderCount(){
        return Math.toIntExact(orderInfoRepository.count());
    }
}
