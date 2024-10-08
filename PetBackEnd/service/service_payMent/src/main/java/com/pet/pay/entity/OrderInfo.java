package com.pet.pay.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("order_info")
public class OrderInfo  extends BaseEntity{

    private String title;//订单标题

    private String orderNo;//商户订单编号

    private String userId;//用户id

    private String petId;//宠物产品id

    private Integer totalFee;//订单金额(分)

    private String codeUrl;//订单二维码连接

    private String orderStatus;//订单状态

    private String destination;//运输目的地
}
