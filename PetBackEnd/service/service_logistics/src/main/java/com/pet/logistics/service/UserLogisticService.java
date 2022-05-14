package com.pet.logistics.service;

import com.pet.logistics.entity.BriefLogisticsInfoReturn;
import com.pet.logistics.repository.OrderInfoRepository;
import com.pet.models.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLogisticService {
    @Autowired
    private OrderInfoRepository orderInfoRepository;

    public List<BriefLogisticsInfoReturn> getByOrderStatus(){
        return orderInfoRepository.findAllByOrderStatus()
    }

}
