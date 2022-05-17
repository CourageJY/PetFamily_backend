package com.pet.pay.task;

import com.pet.pay.entity.OrderInfo;
import com.pet.pay.entity.RefundInfo;
import com.pet.pay.service.OrderInfoService;
import com.pet.pay.service.RefundInfoService;
import com.pet.pay.service.WxPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class WxPayTask {

    @Resource
    private OrderInfoService orderInfoService;

    @Resource
    private WxPayService wxPayService;

    @Resource
    private RefundInfoService refundInfoService;

    /**
     * 秒 分 时 日 月 周
     * 以秒为例
     * *：每秒都执行
     * 1-3：从第1秒开始执行，到第3秒结束执行
     * 0/3：从第0秒开始，每隔3秒执行1次
     * 1,2,3：在指定的第1、2、3秒执行
     * ?：不指定
     * 日和周不能同时制定，指定其中之一，则另一个设置为?
     */
    //@Scheduled(cron = "0/3 * * * * ?")
    public void task1(){
        log.info("task1 被执行......");
    }

    /**
     * 从第0秒开始每隔5秒执行1次查看5s前的订单是否成功
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void orderSuccessConfirm() throws Exception {
        log.info("orderSuccessConfirm 被执行......");

        List<OrderInfo> orderInfoList = orderInfoService.getNoPayOrderBySecondsDuration(5);

        for (OrderInfo orderInfo : orderInfoList) {
            String orderNo = orderInfo.getOrderNo();
            log.warn("成功订单 ===> {}", orderNo);
            //核实订单状态：调用微信支付查单接口
            wxPayService.checkOrderSuccessStatus(orderNo);
        }
    }

    /**
     * 从第0秒开始每隔5min执行1次，查询创建超过5分钟，并且未支付的订单
     */
    @Scheduled(cron = "* 0/5 * * * ?")
    public void orderClosedConfirm() throws Exception {
        log.info("orderClosedConfirm 被执行......");

        List<OrderInfo> orderInfoList = orderInfoService.getNoPayOrderByMinutesDuration(5);

        for (OrderInfo orderInfo : orderInfoList) {
            String orderNo = orderInfo.getOrderNo();
            log.warn("超时订单 ===> {}", orderNo);
            //核实订单状态：调用微信支付查单接口
            wxPayService.checkOrderClosedStatus(orderNo);
        }
    }
}
