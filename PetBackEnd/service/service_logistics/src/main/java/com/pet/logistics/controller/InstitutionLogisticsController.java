package com.pet.logistics.controller;

import com.pet.logistics.entity.BriefLogisticsInfoReturn;
import com.pet.logistics.entity.LogisticsLocationRequest;
import com.pet.logistics.entity.LogisticsStatusRequest;
import com.pet.logistics.service.InstitutionLogisticsService;
import com.pet.models.OrderInfo;
import com.pet.util.config.NeedToken;
import com.pet.util.enums.Role;
import com.pet.util.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/logistics/institution")
@RefreshScope
@Api(value="logisticsInstitution",tags = "物流机构方面的接口")
public class InstitutionLogisticsController {
    @Autowired
    private InstitutionLogisticsService institutionLogisticsService;

    @ApiOperation(value="查询待运输订单列表")
    @NeedToken(role = Role.Institution)
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result<List<BriefLogisticsInfoReturn>> WaitingList()
    {
        List<OrderInfo> orderInfoList=institutionLogisticsService.getWaitingOrders();
        List<BriefLogisticsInfoReturn> briefLogisticsInfoReturns =new ArrayList<>();
        for(OrderInfo o:orderInfoList)
        {
            BriefLogisticsInfoReturn briefLogisticsInfoReturn =new BriefLogisticsInfoReturn(o.getId(),o.getPetId(),o.getUserId(),o.getDestination(),o.getLogisticsStatus());
            briefLogisticsInfoReturns.add(briefLogisticsInfoReturn);
        }
        if(briefLogisticsInfoReturns.isEmpty())
        {
            return Result.wrapErrorResult("不存在待运输订单");
        }
        else
        {
            return Result.wrapSuccessfulResult(briefLogisticsInfoReturns);
        }

    }

    @NeedToken(role = Role.Institution)
    @ApiOperation(value = "机构上传运输位置")
    @RequestMapping(value = "/location",method = RequestMethod.POST)
    public Result<String> updateLocation(@RequestBody LogisticsLocationRequest logisticsLocationRequest)
    {
        if(institutionLogisticsService.updateLocation(logisticsLocationRequest)){
            return Result.wrapSuccessfulResult("更新位置信息成功");
        }
        else {
            return Result.wrapErrorResult("更新位置信息失败");
        }
    }

    @NeedToken(role = Role.Institution)
    @ApiOperation(value = "机构修改运输状态")
    @RequestMapping(value = "/status",method = RequestMethod.POST)
    public Result<String> updateStatus(@RequestBody LogisticsStatusRequest logisticsStatusRequest)
    {
        if(institutionLogisticsService.updateStatus(logisticsStatusRequest)){
            return Result.wrapSuccessfulResult("更新订单状态信息成功");
        }
        else {
            return Result.wrapErrorResult("更新订单状态信息失败");
        }
    }
}
