package com.pet.logistics.service;

import com.pet.logistics.entity.BriefLogisticsInfoReturn;
import com.pet.logistics.entity.LogisticsLocationRequest;
import com.pet.logistics.entity.LogisticsStatusRequest;
import com.pet.logistics.repository.LogisticsRepository;
import com.pet.logistics.repository.OrderInfoRepository;
import com.pet.models.LogisticsInfo;
import com.pet.models.OrderInfo;
import com.pet.util.enums.LogisticsState;
import com.pet.util.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class InstitutionLogisticsService {
    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private LogisticsRepository logisticsRepository;

    public List<LogisticsInfo> getOrders(String status)
    {
        if(Objects.equals(status, "All"))
        {
            return (List<LogisticsInfo>) logisticsRepository.findAll();
        }
        else
        {
            return logisticsRepository.findAllByLogisticsStatus(status);
        }

    }

    public Boolean updateLocation(LogisticsLocationRequest logisticsLocationRequest)
    {
        Optional<LogisticsInfo> getLogisticsInfo=logisticsRepository.findById(logisticsLocationRequest.orderNo);
        LogisticsInfo logisticsInfo=getLogisticsInfo.orElse(null);
        if(logisticsInfo==null)
            return false;
        else
        {
            logisticsInfo.setLocationX(logisticsLocationRequest.location_X);
            logisticsInfo.setLocationY(logisticsLocationRequest.location_Y);
            logisticsRepository.save(logisticsInfo);
            return true;
        }
    }

    public Boolean updateStatus(LogisticsStatusRequest logisticsStatusRequest)
    {
        Optional<LogisticsInfo> getLogisticsInfo=logisticsRepository.findById(logisticsStatusRequest.orderNo);
        LogisticsInfo logisticsInfo=getLogisticsInfo.orElse(null);
        if(logisticsInfo==null)
            return false;
        else
        {
            logisticsInfo.setLogisticsStatus(logisticsStatusRequest.logisticsStatus);
            logisticsRepository.save(logisticsInfo);
            return true;
        }
    }
}
