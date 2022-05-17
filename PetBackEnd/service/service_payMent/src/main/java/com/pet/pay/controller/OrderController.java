package com.pet.pay.controller;

import com.pet.pay.entity.OrderInfo;
import com.pet.pay.enums.OrderStatus;
import com.pet.pay.service.OrderInfoService;
import com.pet.pay.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/pay/order")
@RefreshScope
@Api(value="order",tags = "订单查询接口")
public class OrderController {
//    @ApiOperation(value="根据宠物信息生成订单")
//    @RequestMapping(value = "/generate",method = RequestMethod.GET)
//    public String OrderGenerate(){
//        return "测试用OK!";
//    }
    @Resource
    private OrderInfoService orderInfoService;

    /**
     * 返回所有的订单信息
     * @return 数据库存储的所有订单信息
     */
    @ApiOperation("订单列表")
    @GetMapping("/list")
    public R list(){
        List<OrderInfo> list = orderInfoService.listOrderByCreateTimeDesc();
        return R.ok().data("list", list);
    }

    @ApiOperation("查找某个用户所有的订单")
    @GetMapping("/user/{userId}")
    public R userList(@PathVariable String userId){
        List<OrderInfo> list = orderInfoService.userListOrderByCreateTimeDesc(userId);
        return R.ok().data("list",list);
    }

    /**
     * 根据某个订单编号查询本地的订单状态
     * @param orderNo 某个订单编号
     * @return 支付成功/支付中的message
     */
    @ApiOperation("查询本地订单状态")
    @GetMapping("/query-order-status/{orderNo}")
    public R queryOrderStatus(@PathVariable String orderNo){

        String orderStatus = orderInfoService.getOrderStatus(orderNo);
        if(OrderStatus.SUCCESS.getType().equals(orderStatus)){
            return R.ok().setMessage("支付成功"); //支付成功
        }

        return R.ok().setCode(101).setMessage("支付中......");
    }
}