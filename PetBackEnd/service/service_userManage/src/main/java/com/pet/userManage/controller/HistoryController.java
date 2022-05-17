package com.pet.userManage.controller;

import com.pet.login.service.UserService;
import com.pet.models.*;
import com.pet.userManage.entity.*;
import com.pet.userManage.service.*;
import com.pet.util.config.NeedToken;
import com.pet.util.enums.Role;
import com.pet.util.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/user/history")
@RefreshScope
@Api(value="history",tags = "获取所有申请表以及发帖的历史")
public class HistoryController {
    @Autowired
    private AdoptApplicationService adopt;

    @Autowired
    private FindApplicationService find;

    @Autowired
    private SendApplicationService send;

    @Autowired
    private ReportApplicationService report;

    @Autowired
    private CommonPostService commonPostService;

    @Autowired
    private HelpPostService helpPostService;

    @Autowired
    private UserService userService;

//    public void setAdopt(AdoptApplicationService adopt){
//        this.adopt = adopt;
//    }
//
//    public void setFind(FindApplicationService find){
//        this.find = find;
//    }
//
//    public void setSend(SendApplicationService send){
//        this.send = send;
//    }
//
//    public void setReport(ReportApplicationService report){
//        this.report = report;
//    }

    @NeedToken(role = Role.NormalUser)
    @ApiOperation(value = "获取领养表历史")
    @RequestMapping(value = "/adopt",method = RequestMethod.GET)
    public Result<List<AdoptBriefInfo>>getAdoptList(@RequestParam("userID") String userID){
        List<AdoptApplication> adoptList = adopt.findALLByUserID(userID);
        List<AdoptBriefInfo> adoptBriefInfos = new LinkedList<>();
        for (AdoptApplication adoptApplication : adoptList) {
            adoptBriefInfos.add(new AdoptBriefInfo(adoptApplication));
        }
        return Result.wrapSuccessfulResult(adoptBriefInfos);
    }

    @NeedToken(role = Role.NormalUser)
    @ApiOperation(value = "获取寻回表历史")
    @RequestMapping(value = "/find",method = RequestMethod.GET)
    public Result<List<FindBriefInfo>>getFindList(@RequestParam("userID") String userID){
        List<FindApplication> findList = find.findALLByUserID(userID);
        List<FindBriefInfo> findBriefInfos = new LinkedList<>();
        for (FindApplication findApplication : findList) {
            findBriefInfos.add(new FindBriefInfo(findApplication));
        }
        return Result.wrapSuccessfulResult(findBriefInfos);
    }

    @NeedToken(role = Role.NormalUser)
    @ApiOperation(value = "获取上报表历史")
    @RequestMapping(value = "/report",method = RequestMethod.GET)
    public Result<List<ReportBriefInfo>>getReportList(@RequestParam("userID") String userID){
        List<ReportApplication> reportList = report.findALLByUserID(userID);
        List<ReportBriefInfo> reportBriefInfos = new LinkedList<>();
        for (ReportApplication reportApplication : reportList) {
            reportBriefInfos.add(new ReportBriefInfo(reportApplication));
        }
        return Result.wrapSuccessfulResult(reportBriefInfos);
    }

    @NeedToken(role = Role.NormalUser)
    @ApiOperation(value = "获取送养表历史")
    @RequestMapping(value = "/send",method = RequestMethod.GET)
    public Result<List<SendBriefInfo>>getSendList(@RequestParam("userID") String userID){
        List<SendApplication> sendList = send.findALLByUserID(userID);
        List<SendBriefInfo> sendBriefInfos = new LinkedList<>();
        for (SendApplication sendApplication : sendList) {
            sendBriefInfos.add(new SendBriefInfo(sendApplication));
        }
        return Result.wrapSuccessfulResult(sendBriefInfos);
    }

    //@NeedToken(role = Role.NormalUser)
    @ApiOperation(value = "返回指定用户id的帖子列表")
    @RequestMapping(value = "/post",method = RequestMethod.GET)
    public Result<List<PostBriefInfo>>getPostInfoList(@RequestParam("userID") String userID){
        User user=userService.getById(userID);
        if(user==null){
            return Result.wrapErrorResult("该用户不存在");
        }

        //普通
        List<CommonPost> commonPosts= commonPostService.getByUser(user);
        List<PostBriefInfo> posts=new ArrayList<>();
        for(CommonPost commonPost:commonPosts){
            PostBriefInfo postBriefInfo=new PostBriefInfo(commonPost);
            posts.add(postBriefInfo);
        }

        //求助
        List<HelpPost> helpPosts= helpPostService.getByUser(user);
        for(HelpPost helpPost:helpPosts){
            PostBriefInfo postBriefInfo=new PostBriefInfo(helpPost);
            posts.add(postBriefInfo);
        }

        Collections.sort(posts);
        return Result.wrapSuccessfulResult(posts);
    }
}
