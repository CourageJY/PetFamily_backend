package com.pet.logistics.service;

import com.pet.logistics.entity.LogisticsLocationRequest;
import com.pet.logistics.entity.LogisticsLocationReturn;
import com.pet.logistics.repository.OrderInfoRepository;
import com.pet.models.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserLogisticsService {
    @Autowired
    private OrderInfoRepository orderInfoRepository;


}
