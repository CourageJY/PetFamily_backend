package com.pet.logistics.service;

import com.pet.logistics.entity.LogisticsLocationRequest;
import com.pet.logistics.repository.OrderInfoRepository;
import com.pet.models.OrderInfo;
import com.pet.util.enums.LogisticsState;
import com.pet.util.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class InstitutionLogisticsService {
    @Autowired
    private OrderInfoRepository orderInfoRepository;

    public List<OrderInfo> getWaitingOrders(){
        //首先获取所有支付成功的订单
        List<OrderInfo> orderInfoList=orderInfoRepository.findAllByOrderStatus(OrderStatus.SUCCESS.toString());
        //对于所有支付成功的订单，如果运输状态为空，则将其置为待运输
        for(OrderInfo o:orderInfoList)
        {
            if (o.getLogisticsStatus() == null)
            {
                o.setLogisticsStatus(LogisticsState.WaitingTransport.toString());//?怎么保存进数据库
                orderInfoRepository.save(o);
            }
        }
        //返回待运输列表
        return orderInfoList;
    }

    public Boolean updateLocation(LogisticsLocationRequest logisticsLocationRequest)
    {
        OrderInfo orderInfo=orderInfoRepository.findById(logisticsLocationRequest.orderID).orElse(null);
        if(orderInfo==null)
            return false;
        if(Objects.equals(orderInfo.getLogisticsStatus(), LogisticsState.WaitingTransport.toString()))
            orderInfo.setLogisticsStatus(LogisticsState.Transporting.toString());
        orderInfo.setLocationX(logisticsLocationRequest.location_X);
        orderInfo.setLocationY(logisticsLocationRequest.location_Y);
        orderInfoRepository.save(orderInfo);
        return true;
    }
}
