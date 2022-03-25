package com.pet.application.controller;

import com.pet.application.entity.AdoptBriefInfo;
import com.pet.application.entity.FindBriefInfo;
import com.pet.application.entity.ReportBriefInfo;
import com.pet.application.entity.SendBriefInfo;
import com.pet.application.service.AdoptApplicationService;
import com.pet.application.service.FindApplicationService;
import com.pet.application.service.ReportApplicationService;
import com.pet.application.service.SendApplicationService;
import com.pet.models.AdoptApplication;
import com.pet.models.FindApplication;
import com.pet.models.ReportApplication;
import com.pet.models.SendApplication;
import com.pet.util.config.NeedToken;
import com.pet.util.enums.ApplicationState;
import com.pet.util.enums.PetState;
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
@RequestMapping("/api/application/getList")
@RefreshScope
@Api(value="application",tags = "得到申请表列表的简略信息")
public class GetApplicaionListController {
    @Autowired
    private AdoptApplicationService adoptApplicationService;

    @Autowired
    private SendApplicationService sendApplicationService;

    @Autowired
    private FindApplicationService findApplicationService;

    @Autowired
    private ReportApplicationService reportApplicationService;

    @NeedToken(role = Role.both)
    @ApiOperation(value="得到领养申请表列表的简略信息，输入为申请表的状态，0：未审核；1：审核不通过；2：审核通过")
    @RequestMapping(value = "/adoptApplicationList",method = RequestMethod.GET)
    public Result<List<AdoptBriefInfo>> getAdoptApplicationList(@RequestParam("state") int state){
        List<AdoptApplication> adoptApplications=new ArrayList<>();
        if(state==0){
            adoptApplications=adoptApplicationService.findByState(ApplicationState.unchecked.toString());
        }
        else if (state==1){
            adoptApplications=adoptApplicationService.findByState(ApplicationState.disapprove.toString());
        }
        else if(state==2){
            adoptApplications=adoptApplicationService.findByState((ApplicationState.approve.toString()));
        }
        else{
            return Result.wrapErrorResult("状态错误，只能为0/1/2");
        }

        List<AdoptBriefInfo> adoptBriefInfos=new ArrayList<>();
        for(AdoptApplication application:adoptApplications){
            adoptBriefInfos.add(new AdoptBriefInfo(application));
        }

        return Result.wrapSuccessfulResult(adoptBriefInfos);
    }

    @NeedToken(role = Role.both)
    @ApiOperation(value="得到送养申请表列表的简略信息，输入为申请表的状态，0：未审核；1：审核不通过；2：审核通过")
    @RequestMapping(value = "/sendApplicationList",method = RequestMethod.GET)
    public Result<List<SendBriefInfo>> getSendApplicationList(@RequestParam("state") int state){
        List<SendApplication> sendApplications=new ArrayList<>();
        if(state==0){
            sendApplications=sendApplicationService.findByState(ApplicationState.unchecked.toString());
        }
        else if (state==1){
            sendApplications=sendApplicationService.findByState(ApplicationState.disapprove.toString());
        }
        else if(state==2){
            sendApplications=sendApplicationService.findByState((ApplicationState.approve.toString()));
        }
        else{
            return Result.wrapErrorResult("状态错误，只能为0/1/2");
        }

        List<SendBriefInfo> sendBriefInfos=new ArrayList<>();
        for(SendApplication application:sendApplications){
            sendBriefInfos.add(new SendBriefInfo(application));
        }

        return Result.wrapSuccessfulResult(sendBriefInfos);
    }

    @NeedToken(role = Role.both)
    @ApiOperation(value="得到寻回申请表列表的简略信息，输入为申请表的状态，0：未审核；1：审核不通过；2：审核通过")
    @RequestMapping(value = "/findApplicationList",method = RequestMethod.GET)
    public Result<List<FindBriefInfo>> getFindApplicationList(@RequestParam("state") int state){
        List<FindApplication> findApplications=new ArrayList<>();
        if(state==0){
            findApplications=findApplicationService.findByState(ApplicationState.unchecked.toString());
        }
        else if (state==1){
            findApplications=findApplicationService.findByState(ApplicationState.disapprove.toString());
        }
        else if(state==2){
            findApplications=findApplicationService.findByState((ApplicationState.approve.toString()));
        }
        else{
            return Result.wrapErrorResult("状态错误，只能为0/1/2");
        }

        List<FindBriefInfo> findBriefInfos=new ArrayList<>();
        for(FindApplication application:findApplications){
            findBriefInfos.add(new FindBriefInfo(application));
        }

        return Result.wrapSuccessfulResult(findBriefInfos);
    }

    @NeedToken(role = Role.both)
    @ApiOperation(value="得到上报申请表列表的简略信息，输入为申请表的状态，0：未审核；1：审核不通过；2：审核通过")
    @RequestMapping(value = "/reportApplicationList",method = RequestMethod.GET)
    public Result<List<ReportBriefInfo>> getReportApplicationList(@RequestParam("state") int state){
        List<ReportApplication> reportApplications=new ArrayList<>();
        if(state==0){
            reportApplications=reportApplicationService.findByState(ApplicationState.unchecked.toString());
        }
        else if (state==1){
            reportApplications=reportApplicationService.findByState(ApplicationState.disapprove.toString());
        }
        else if(state==2){
            reportApplications=reportApplicationService.findByState((ApplicationState.approve.toString()));
        }
        else{
            return Result.wrapErrorResult("状态错误，只能为0/1/2");
        }

        List<ReportBriefInfo> reportBriefInfos=new ArrayList<>();
        for(ReportApplication application:reportApplications){
            reportBriefInfos.add(new ReportBriefInfo(application));
        }

        return Result.wrapSuccessfulResult(reportBriefInfos);
    }



}

