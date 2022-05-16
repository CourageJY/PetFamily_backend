package com.pet.logistics.controller;

import com.pet.logistics.entity.BriefLogisticsInfoReturn;
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

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/logistics/user")
@RefreshScope
@Api(value="logisticsUser",tags = "物流用户方面的接口")
public class UserLogisticsController {

    @Autowired
    private UserLogisticsService userLogisticsService;

    @NeedToken(role = Role.NormalUser)
    @ApiOperation(value = "获取订单位置信息")
    @RequestMapping(value = "/location",method = RequestMethod.GET)
    public Result<LogisticsLocationReturn> getLocation(@RequestParam("orderNo") String orderNo)
    {
        LogisticsLocationReturn logisticsLocationReturn=userLogisticsService.getLocation(orderNo);
        if(logisticsLocationReturn==null)
        {
            return Result.wrapErrorResult("不存在该订单！");
        }
        return Result.wrapSuccessfulResult(logisticsLocationReturn);
    }


}
