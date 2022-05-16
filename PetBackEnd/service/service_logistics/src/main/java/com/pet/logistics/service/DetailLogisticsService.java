package com.pet.logistics.service;

import com.pet.logistics.entity.DetailLogisticsInfoReturn;
import com.pet.logistics.repository.LogisticsRepository;
import com.pet.logistics.repository.OrderInfoRepository;
import com.pet.models.LogisticsInfo;
import com.pet.models.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DetailLogisticsService {
    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private LogisticsRepository logisticsRepository;

    public DetailLogisticsInfoReturn getDetailInfo(String orderNo)
    {
        OrderInfo orderInfo=orderInfoRepository.findByOrderNo(orderNo);
        Optional<LogisticsInfo> tryGetLogisticsInfo=logisticsRepository.findById(orderNo);
        LogisticsInfo logisticsInfo=tryGetLogisticsInfo.orElse(null);
        if(logisticsInfo==null)
            return null;
        else
            return new DetailLogisticsInfoReturn(orderNo,orderInfo.getPetId(),orderInfo.getUserId(),logisticsInfo.getLocationX(),logisticsInfo.getLocationY(),logisticsInfo.getLogisticsTime(), logisticsInfo.getDestination(), logisticsInfo.getLogisticsStatus());
    }
}
