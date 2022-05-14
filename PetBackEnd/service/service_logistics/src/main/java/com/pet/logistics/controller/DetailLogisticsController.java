package com.pet.logistics.controller;

import com.pet.logistics.entity.DetailLogisticsInfoReturn;
import com.pet.logistics.service.DetailLogisticsService;
import com.pet.models.OrderInfo;
import com.pet.util.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/logistics")
@RefreshScope
@Api(value="logisticsDetail",tags = "物流方面的通用接口")
public class DetailLogisticsController {
    @Autowired
    private DetailLogisticsService detailLogisticsService;

    @ApiOperation(value="查询订单详细信息")
    @RequestMapping(value= "/detail",method = RequestMethod.GET)
    public Result<DetailLogisticsInfoReturn> detailLogisticsInfoReturnResult(@RequestParam("orderID") String orderID)
    {
        OrderInfo orderInfo = detailLogisticsService.getById(orderID);
        DetailLogisticsInfoReturn detailLogisticsInfoReturn=new DetailLogisticsInfoReturn(orderInfo.getId(),orderInfo.getPetId(),
                orderInfo.getUserId(),orderInfo.getLocationX(),orderInfo.getLocationY(),orderInfo.getLogisticsTime(),
                orderInfo.getDestination(),orderInfo.getLogisticsStatus());
        if(detailLogisticsInfoReturn==null){
            return Result.wrapErrorResult("不存在该订单！");
        }
        return Result.wrapSuccessfulResult(detailLogisticsInfoReturn);
    }
}
