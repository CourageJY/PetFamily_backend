package com.pet.logistics.controller;

import com.pet.logistics.entity.LogisticsLocationRequest;
import com.pet.logistics.entity.LogisticsLocationReturn;
import com.pet.logistics.service.DetailLogisticsService;
import com.pet.logistics.service.UserLogisticsService;
import com.pet.models.OrderInfo;
import com.pet.util.config.NeedToken;
import com.pet.util.enums.Role;
import com.pet.util.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/logistics/user")
@RefreshScope
@Api(value="logisticsUser",tags = "物流用户方面的接口")
public class UserLogisticsController {
    @Autowired
    private DetailLogisticsService detailLogisticsService;

    @Autowired
    private UserLogisticsService userLogisticsService;

    @NeedToken(role = Role.NormalUser)
    @ApiOperation(value = "获取订单位置信息")
    @RequestMapping(value = "/location",method = RequestMethod.GET)
    public Result<LogisticsLocationReturn> getLocation(@RequestParam("orderID") String orderID)
    {
        OrderInfo orderInfo=detailLogisticsService.getById(orderID);
        if(orderInfo==null){
            return Result.wrapErrorResult("该订单不存在");
        }
        LogisticsLocationReturn logisticsLocationReturn=new LogisticsLocationReturn(orderInfo.getLocationX(), orderInfo.getLocationY());
        return Result.wrapSuccessfulResult(logisticsLocationReturn);
    }


}
