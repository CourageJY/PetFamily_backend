package com.pet.community.controller;

import com.pet.community.entity.*;
import com.pet.community.service.CommonPostService;
import com.pet.community.service.HelpPostService;
import com.pet.login.service.UserService;
import com.pet.minio.service.MinIOService;
import com.pet.models.CommonPost;
import com.pet.models.HelpPost;
import com.pet.models.User;
import com.pet.util.config.NeedToken;
import com.pet.util.enums.PostState;
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
@RequestMapping("api/helpPost")
@RefreshScope
@Api(value="helpPost",tags = "求助帖子")
public class HelpPostController {
    @Autowired
    private HelpPostService helpPostService;

    @Autowired
    private MinIOService minIOService;

    @Autowired
    private UserService userService;

    @ApiOperation(value="用id查询帖子的详细信息")
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public Result<HelpPostDetail> HelpPostDetial(@RequestParam("id") String id)
    {
        HelpPost helpPost=helpPostService.getByID(id);
        if(helpPost==null){
            return Result.wrapErrorResult("不存在该帖子！");
        }
        HelpPostDetail helpPostDetail=new HelpPostDetail(helpPost);
        return Result.wrapSuccessfulResult(helpPostDetail);
    }

    @ApiOperation(value="查询满足指定条件的求助帖子的列表，若content为空，则返回所有帖子")
    @RequestMapping(value = "/briefList",method = RequestMethod.GET)
    public Result<List<PostBriefInfo>> HelpPostList(String content)
    {
        List<HelpPost> helpPosts= helpPostService.getAll();
        List<PostBriefInfo> posts=new ArrayList<>();
        for(HelpPost helpPost:helpPosts){
            PostBriefInfo postBriefInfo=new PostBriefInfo(helpPost);
            posts.add(postBriefInfo);
        }
        if(content==null){
            return Result.wrapSuccessfulResult(posts);
        }
        else{
            List<PostBriefInfo> resultPosts=new ArrayList<>();
            for(PostBriefInfo postBriefInfo:posts){
                if(postBriefInfo.getContent().contains(content)){
                    resultPosts.add(postBriefInfo);
                }
            }
            return Result.wrapSuccessfulResult(resultPosts);
        }
    }

    @ApiOperation(value="查询某个用户的求助帖子的列表")
    @RequestMapping(value = "/userBriefList",method = RequestMethod.GET)
    public Result<List<PostBriefInfo>> UserHelpPostList(HttpServletRequest httpServletRequest)
    {
        //获取user
        User user= userService.getById(JwtUtil.getIDByToken(httpServletRequest));

        List<HelpPost> helpPosts= helpPostService.findAllByUserId(user);

        List<PostBriefInfo> posts=new ArrayList<>();
        for(HelpPost helpPost:helpPosts){
            PostBriefInfo postBriefInfo=new PostBriefInfo(helpPost);
            posts.add(postBriefInfo);
        }

        return Result.wrapSuccessfulResult(posts);

    }

    @NeedToken(role = Role.both)
    @ApiOperation(value="用id删除指定帖子")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public Result<String> DeleteHelpPost(String id)
    {
        HelpPost helpPost=helpPostService.getByID(id);
        if(id==null){
            return Result.wrapErrorResult("该帖子不存在");
        }
        //先从minIO中删除对应图片
        if(helpPost.getPhotoOne()!=null){
            String[] url = helpPost.getPhotoOne().split("/");
            String file=url[url.length-1];
            minIOService.removeFile(file);
        }
        if(helpPost.getPhotoTwo()!=null){
            String[] url = helpPost.getPhotoTwo().split("/");
            String file=url[url.length-1];
            minIOService.removeFile(file);
        }

        helpPostService.deleteByID(id);
        return Result.wrapSuccessfulResult("删除成功");
    }

    @NeedToken(role = Role.NormalUser)
    @ApiOperation(value="创建单个求助帖子")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public Result<String> CreateHelpPost(@RequestBody HelpPostCreateInfo helpPostCreateInfo,
                                           HttpServletRequest httpServletRequest)
    {
        //获取user
        User user= userService.getById(JwtUtil.getIDByToken(httpServletRequest));

        HelpPost helpPost=new HelpPost();
        helpPost.setId(helpPostService.generateID());
        helpPost.setUser(user);
        helpPost.setTime(TimeConvert.getNowTime());
        helpPost.setThumbUp(0);
        helpPost.setContent(helpPostCreateInfo.getContent());
        helpPost.setStatus(PostState.posted.toString());
        helpPost.setTitle(helpPostCreateInfo.getTitle());
        helpPost.setLostPlace(helpPostCreateInfo.getLost_place());
        helpPost.setLostTime(helpPostCreateInfo.getLost_time());
        //最多存储两张图片
        if(helpPostCreateInfo.getPhotos().length>=1){
            helpPost.setPhotoOne(helpPostCreateInfo.getPhotos()[0]);
        }
        if(helpPostCreateInfo.getPhotos().length>=2){
            helpPost.setPhotoTwo(helpPostCreateInfo.getPhotos()[1]);
        }

        helpPostService.createOrUpdate(helpPost);
        return Result.wrapSuccessfulResult("创建成功");
    }

    @NeedToken(role = Role.both)
    @ApiOperation(value="修改单个帖子，其中id为欲修改的帖子编号")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result<String> UpdateCommonPost(@RequestBody HelpPostUpdateInfo helpPostUpdateInfo,
                                           HttpServletRequest httpServletRequest)
    {
        HelpPost helpPost=helpPostService.getByID( helpPostUpdateInfo.getId());
        if(helpPost==null){
            return Result.wrapErrorResult("不存在该帖子");
        }
        //先从minIO中删除对应图片
        if(helpPostUpdateInfo.getPhotos().length>0){
            if(helpPost.getPhotoOne()!=null){
                String[] url = helpPost.getPhotoOne().split("/");
                String file=url[url.length-1];
                minIOService.removeFile(file);
            }
            if(helpPost.getPhotoTwo()!=null){
                String[] url = helpPost.getPhotoTwo().split("/");
                String file=url[url.length-1];
                minIOService.removeFile(file);
            }
        }
        if(helpPostUpdateInfo.content!=null){
            helpPost.setContent(helpPostUpdateInfo.content);
        }
        if(helpPostUpdateInfo.title!=null){
            helpPost.setTitle(helpPostUpdateInfo.title);
        }
        helpPostService.createOrUpdate(helpPost);

        return Result.wrapSuccessfulResult("修改成功");
    }
}
