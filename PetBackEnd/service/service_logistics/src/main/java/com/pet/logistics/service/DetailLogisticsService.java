package com.pet.logistics.service;

import com.pet.logistics.repository.OrderInfoRepository;
import com.pet.models.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DetailLogisticsService {
    @Autowired
    private OrderInfoRepository orderInfoRepository;
    public OrderInfo getById(String orderID)
    {
        Optional<OrderInfo> order=orderInfoRepository.findById(orderID);
        return order.orElse(null);
    }
}
