package com.pet.pay.service;

import com.pet.pay.entity.OrderGenerate;

import java.security.GeneralSecurityException;
import java.util.Map;

public interface WxPayService {
    Map<String, Object> nativePay(OrderGenerate orderGenerate) throws Exception;

    void processOrder(Map<String, Object> bodyMap) throws GeneralSecurityException;

    void cancelOrder(String orderNo) throws Exception;

    String queryOrder(String orderNo) throws Exception;

    void checkOrderSuccessStatus(String orderNo) throws Exception;

    void checkOrderClosedStatus(String orderNo) throws Exception;

    void refund(String orderNo, String reason) throws Exception;

    String queryRefund(String orderNo) throws Exception;

    void checkRefundStatus(String refundNo) throws Exception;

    void processRefund(Map<String, Object> bodyMap) throws Exception;

    String queryBill(String billDate, String type) throws Exception;

    String downloadBill(String billDate, String type) throws Exception;
}
