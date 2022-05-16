package com.pet.logistics.service;

import com.pet.logistics.entity.BriefLogisticsInfoReturn;
import com.pet.logistics.entity.LogisticsLocationRequest;
import com.pet.logistics.entity.LogisticsLocationReturn;
import com.pet.logistics.repository.LogisticsRepository;
import com.pet.logistics.repository.OrderInfoRepository;
import com.pet.models.LogisticsInfo;
import com.pet.models.OrderInfo;
import com.pet.util.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserLogisticsService {
    @Autowired
    private LogisticsRepository logisticsRepository;

    public LogisticsLocationReturn getLocation (String orderNo)
    {
        Optional<LogisticsInfo> tryGetLogisticsInfo=logisticsRepository.findById(orderNo);
        LogisticsInfo logisticsInfo=tryGetLogisticsInfo.orElse(null);
        if(logisticsInfo==null)
            return null;
        else
            return new LogisticsLocationReturn(logisticsInfo.getLocationX(), logisticsInfo.getLocationY());
    }
}
