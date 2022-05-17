package com.pet.pay.service;

import com.pet.pay.entity.OrderGenerate;
import com.pet.pay.entity.OrderInfo;
import com.pet.pay.enums.OrderStatus;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface OrderInfoService extends IService<OrderInfo> {

    OrderInfo createOrderByPetId(OrderGenerate orderGenerate);

    void saveCodeUrl(String orderNo, String codeUrl);

    List<OrderInfo> listOrderByCreateTimeDesc();

    List<OrderInfo> userListOrderByCreateTimeDesc(String userId);

    void updateStatusByOrderNo(String orderNo, OrderStatus orderStatus);

    String getOrderStatus(String orderNo);

    List<OrderInfo> getNoPayOrderByDuration(int minutes);

    OrderInfo getOrderByOrderNo(String orderNo);
}
