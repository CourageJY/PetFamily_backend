package com.pet.logistics.controller;

import com.pet.logistics.entity.BriefLogisticsInfoReturn;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
