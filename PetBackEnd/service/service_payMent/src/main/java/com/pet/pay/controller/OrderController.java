package com.pet.pay.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("api/pay/order")
@RefreshScope
@Api(value="order",tags = "订单方面的接口")
public class OrderController {
    @ApiOperation(value="根据宠物信息生成订单")
    @RequestMapping(value = "/generate",method = RequestMethod.GET)
    public String OrderGenerate(){
        return "测试用OK!";
    }
}
