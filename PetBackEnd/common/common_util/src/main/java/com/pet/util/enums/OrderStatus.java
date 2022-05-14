package com.pet.util.enums;

public enum OrderStatus {
    //未支付 支付成功 超时已关闭 用户已取消 退款中 已退款 退款异常
    Unpaid,//未支付
    Success,//支付成功
    Timeout,//超时
    Cancelled//已取消
}
