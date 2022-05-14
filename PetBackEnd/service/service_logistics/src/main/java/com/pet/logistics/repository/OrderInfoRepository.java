package com.pet.logistics.repository;

import com.pet.logistics.entity.BriefLogisticsInfoReturn;
import com.pet.models.Comment;
import com.pet.models.OrderInfo;
import com.pet.models.Pet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderInfoRepository extends CrudRepository<OrderInfo,String> {
    List<BriefLogisticsInfoReturn> findAllByOrderStatus(String s);
}
