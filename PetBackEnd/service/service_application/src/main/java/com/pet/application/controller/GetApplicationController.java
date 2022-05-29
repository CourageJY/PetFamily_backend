package com.pet.application.controller;

import com.alibaba.fastjson.JSONObject;
import com.pet.application.service.AdoptApplicationService;
import com.pet.application.service.FindApplicationService;
import com.pet.application.service.ReportApplicationService;
import com.pet.application.service.SendApplicationService;
import com.pet.models.AdoptApplication;
import com.pet.models.FindApplication;
import com.pet.models.ReportApplication;
import com.pet.models.SendApplication;
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
@RequestMapping("/api/application/getOne")
@RefreshScope
@Api(value="application",tags = "得到单个申请表")
public class GetApplicationController {
    @Autowired
    private AdoptApplicationService adoptApplicationService;

    @Autowired
    private SendApplicationService sendApplicationService;

    @Autowired
    private FindApplicationService findApplicationService;

    @Autowired
    private ReportApplicationService reportApplicationService;

    //@NeedToken(role = Role.both)
    @ApiOperation(value="获取单个领养申请表详细信息，输入为申请表ID")
    @RequestMapping(value = "/adoptApplication",method = RequestMethod.GET)
    public Result<JSONObject> getAdoptApplication(@RequestParam("application_id") String applicationID){
        AdoptApplication adoptApplication=adoptApplicationService.findByID(applicationID);
        if(adoptApplication==null){
            return Result.wrapErrorResult("该申请表ID不存在");
        }

        //构建并返回Json对象
        JSONObject Json=new JSONObject();
        Json.put("user",adoptApplication.getUser());
        Json.put("applicationId",adoptApplication.getId());
        Json.put("pet",adoptApplication.getPet());
        Json.put("title",adoptApplication.getTitle());
        Json.put("time",adoptApplication.getTime());
        Json.put("economicCondition",adoptApplication.getEconomicCondition());
        Json.put("reason",adoptApplication.getReason());
        Json.put("area",adoptApplication.getArea());
        Json.put("status",adoptApplication.getStatus());
        Json.put("phoneNum",adoptApplication.getPhoneNum());

        return Result.wrapSuccessfulResult(Json);
    }

    @NeedToken(role = Role.both)
    @ApiOperation(value="获取单个上报申请表详细信息，输入为申请表ID")
    @RequestMapping(value = "/reportApplication",method = RequestMethod.GET)
    public Result<JSONObject> getReportApplication(@RequestParam("application_id") String applicationID){
        ReportApplication reportApplication=reportApplicationService.findByID(applicationID);
        if(reportApplication==null){
            return Result.wrapErrorResult("该申请表ID不存在");
        }

        //构建并返回Json对象
        JSONObject Json=new JSONObject();
        Json.put("user",reportApplication.getUser());
        Json.put("applicationId",reportApplication.getId());
        Json.put("pet",reportApplication.getPet());
        Json.put("title",reportApplication.getTitle());
        Json.put("time",reportApplication.getTime());
        Json.put("locationX",reportApplication.getLocationX());
        Json.put("locationY",reportApplication.getLocationY());
        Json.put("description",reportApplication.getDescription());
        Json.put("photoOne",reportApplication.getPhotoOne());
        Json.put("photoTwo",reportApplication.getPhotoTwo());
        Json.put("status",reportApplication.getStatus());
        Json.put("phoneNum",reportApplication.getPhoneNum());

        return Result.wrapSuccessfulResult(Json);
    }

    @NeedToken(role = Role.both)
    @ApiOperation(value="获取单个寻回申请表详细信息，输入为申请表ID")
    @RequestMapping(value = "/findApplication",method = RequestMethod.GET)
    public Result<JSONObject> getFindApplication(@RequestParam("application_id") String applicationID){
        FindApplication findApplication=findApplicationService.findByID(applicationID);
        if(findApplication==null){
            return Result.wrapErrorResult("该申请表ID不存在");
        }

        //构建并返回Json对象
        JSONObject Json=new JSONObject();
        Json.put("user",findApplication.getUser());
        Json.put("applicationId",findApplication.getId());
        Json.put("pet",findApplication.getPet());
        Json.put("title",findApplication.getTitle());
        Json.put("time",findApplication.getTime());
        Json.put("description",findApplication.getDescription());
        Json.put("petPhoto",findApplication.getPetPhoto());
        Json.put("certificate",findApplication.getCertificate());
        Json.put("status",findApplication.getStatus());
        Json.put("phoneNum",findApplication.getPhoneNum());
        Json.put("area",findApplication.getArea());

        return Result.wrapSuccessfulResult(Json);
    }

    @NeedToken(role = Role.both)
    @ApiOperation(value="获取单个送养申请表详细信息，输入为申请表ID")
    @RequestMapping(value = "/sendApplication",method = RequestMethod.GET)
    public Result<JSONObject> getSendApplication(@RequestParam("application_id") String applicationID){
        SendApplication sendApplication=sendApplicationService.findByID(applicationID);
        if(sendApplication==null){
            return Result.wrapErrorResult("该申请表ID不存在");
        }

        //构建并返回Json对象
        JSONObject Json=new JSONObject();
        Json.put("user",sendApplication.getUser());
        Json.put("applicationId",sendApplication.getId());
        Json.put("pet",sendApplication.getPet());
        Json.put("title",sendApplication.getTitle());
        Json.put("time",sendApplication.getTime());
        Json.put("location",sendApplication.getLocation());
        Json.put("reason",sendApplication.getReason());
        Json.put("certificate",sendApplication.getCertificate());
        Json.put("petPhoto",sendApplication.getPetPhoto());
        Json.put("sendTime",sendApplication.getSendTime());
        Json.put("status",sendApplication.getStatus());
        Json.put("phoneNum",sendApplication.getPhoneNum());

        return Result.wrapSuccessfulResult(Json);
    }


}
