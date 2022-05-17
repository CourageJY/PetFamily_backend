package com.pet.community.controller;

import com.pet.community.entity.ReportPostCreateInfo;
import com.pet.community.entity.ReportPostReturnInfo;
import com.pet.community.repository.CommonPostRepository;
import com.pet.community.repository.HelpPostRepository;
import com.pet.community.service.ReportPostService;
import com.pet.login.service.UserService;
import com.pet.models.ReportPost;
import com.pet.util.config.NeedToken;
import com.pet.util.enums.Role;
import com.pet.util.utils.JwtUtil;
import com.pet.util.utils.Result;
import com.pet.util.utils.TimeConvert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/community/reportPost")
@RefreshScope
@Api(value="reportpost",tags = "举报内容管理")
public class ReportPostController {
    @Autowired
    private ReportPostService reportPostService;

    @Autowired
    private UserService userService;

    @Autowired
    private HelpPostRepository helpPostRepository;

    @Autowired
    private CommonPostRepository commonPostRepository;

    //@NeedToken(role = Role.both)
    @ApiOperation(value="用id查询举报的详细信息")
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public Result<ReportPost> ReportDetial(@RequestParam("id") String id)
    {
        ReportPost reportPost=reportPostService.getByID(id);
        if(reportPost==null){
            return Result.wrapErrorResult("不存在该举报内容！");
        }
        return Result.wrapSuccessfulResult(reportPost);
    }

    //@NeedToken(role = Role.both)
    @ApiOperation(value="查询该帖子的所有举报列表")
    @RequestMapping(value = "/reportpostList",method = RequestMethod.GET)
    public Result<List<ReportPostReturnInfo>> ReportPostList(String postId)
    {
        List<ReportPost> reportPosts= reportPostService.getAllByPostId(postId);

        List<ReportPostReturnInfo> reportPostReturnInfos=new ArrayList<>();
        for(ReportPost reportPost:reportPosts){
            ReportPostReturnInfo reportPostReturnInfo=new ReportPostReturnInfo(reportPost);
            reportPostReturnInfos.add(reportPostReturnInfo);
        }
        return Result.wrapSuccessfulResult(reportPostReturnInfos);
    }

    //@NeedToken(role = Role.both)
    @ApiOperation(value="用id删除指定举报")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public Result<String> DeleteComment(String id)
    {
        ReportPost reportPost=reportPostService.getByID(id);
        if(id==null){
            return Result.wrapErrorResult("该举报不存在");
        }

        reportPostService.deleteByID(id);
        return Result.wrapSuccessfulResult("删除成功");
    }

    //@NeedToken(role = Role.NormalUser)
    @ApiOperation(value="创建单个举报")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public Result<String> CreateComment(@RequestBody ReportPostCreateInfo reportPostCreateInfo,
                                        HttpServletRequest httpServletRequest)
    {
        if(!commonPostRepository.existsById(reportPostCreateInfo.postId)&&!helpPostRepository.existsById(reportPostCreateInfo.postId)){
            return Result.wrapErrorResult("该帖子不存在！");
        }

        ReportPost reportPost=new ReportPost();
        reportPost.setId(reportPostService.generateID());
        reportPost.setContent(reportPostCreateInfo.getContent());
        reportPost.setTime(TimeConvert.getNowTime());
        reportPost.setUser(userService.getById(JwtUtil.getIDByToken(httpServletRequest)));
        reportPost.setPostId(reportPostCreateInfo.getPostId());
        reportPost.setReason(reportPostCreateInfo.getReason());

        reportPostService.createOrUpdate(reportPost);
        return Result.wrapSuccessfulResult("创建成功");
    }

}