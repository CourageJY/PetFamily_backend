package com.pet.pay.service.impl;

import com.pet.pay.entity.OrderGenerate;
import com.pet.pay.entity.OrderInfo;
import com.pet.pay.entity.Product;
import com.pet.pay.enums.OrderStatus;
import com.pet.pay.mapper.OrderInfoMapper;
import com.pet.pay.mapper.ProductMapper;
import com.pet.pay.service.OrderInfoService;
import com.pet.pay.util.OrderNoUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Override
    public OrderInfo createOrderByPetId(OrderGenerate orderGenerate) {

        //查找已存在但未支付的订单，如果有该宠物Id，直接返回，防止重复下单
        OrderInfo orderInfo = this.getNoPayOrderByPetId(orderGenerate.getPetId());
        if( orderInfo != null){
            return orderInfo;
        }

        //获取商品信息
        Product product = productMapper.selectById(orderGenerate.getPetId());

        //生成订单
        orderInfo = new OrderInfo();
        orderInfo.setTitle("宠物服务");
        orderInfo.setOrderNo(OrderNoUtils.getOrderNo()); //订单号
        orderInfo.setPetId(orderGenerate.getPetId());
        if(product == null)
            orderInfo.setTotalFee(1);//默认为0.01元
        else orderInfo.setTotalFee(product.getPrice()); //分
        orderInfo.setOrderStatus(OrderStatus.NOTPAY.getType());
        orderInfo.setDestination(orderGenerate.getDestination());
        orderInfo.setUserId(orderGenerate.getUserId());
        baseMapper.insert(orderInfo);

        return orderInfo;
    }

    /**
     * 存储订单二维码
     */
    @Override
    public void saveCodeUrl(String orderNo, String codeUrl) {

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCodeUrl(codeUrl);

        baseMapper.update(orderInfo, queryWrapper);
    }

    /**
     * 查询订单列表，并倒序查询
     */
    @Override
    public List<OrderInfo> listOrderByCreateTimeDesc() {

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<OrderInfo>().orderByDesc("create_time");
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询某个用户订单列表，并倒序查询
     */
    @Override
    public List<OrderInfo> userListOrderByCreateTimeDesc(String userId) {

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<OrderInfo>().eq("user_id",userId).orderByDesc("create_time");
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 根据订单号更新订单状态
     */
    @Override
    public void updateStatusByOrderNo(String orderNo, OrderStatus orderStatus) {

        log.info("更新订单状态 ===> {}", orderStatus.getType());

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderStatus(orderStatus.getType());

        baseMapper.update(orderInfo, queryWrapper);
    }

    /**
     * 根据订单号获取订单状态
     */
    @Override
    public String getOrderStatus(String orderNo) {

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        OrderInfo orderInfo = baseMapper.selectOne(queryWrapper);
        if(orderInfo == null){
            return null;
        }
        return orderInfo.getOrderStatus();
    }

    /**
     * 查询创建超过seconds秒数并且未支付的订单
     */
    @Override
    public List<OrderInfo> getNoPayOrderBySecondsDuration(int seconds) {

        Instant instant = Instant.now().minus(Duration.ofSeconds(seconds));

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_status", OrderStatus.NOTPAY.getType());
        queryWrapper.le("create_time", instant);

        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询创建超过minutes分钟并且未支付的订单
     */
    @Override
    public List<OrderInfo> getNoPayOrderByMinutesDuration(int minutes) {

        Instant instant = Instant.now().minus(Duration.ofMinutes(minutes));

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_status", OrderStatus.NOTPAY.getType());
        queryWrapper.le("create_time", instant);

        return baseMapper.selectList(queryWrapper);
    }
    /**
     * 根据订单号获取订单
     */
    @Override
    public OrderInfo getOrderByOrderNo(String orderNo) {

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);

        return baseMapper.selectOne(queryWrapper);
    }


    /**
     * 根据商品id查询未支付订单
     * 防止重复创建订单对象
     * @param petId 产品Id
     * @return OrderInfo实体类
     */
    private OrderInfo getNoPayOrderByPetId(String petId) {

        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pet_id", petId);
        queryWrapper.eq("order_status", OrderStatus.NOTPAY.getType());
//        queryWrapper.eq("user_id", userId);
        OrderInfo orderInfo;
        orderInfo = baseMapper.selectOne(queryWrapper);
        return orderInfo;
    }


}
