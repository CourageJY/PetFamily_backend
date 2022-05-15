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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/community/commonPost")
@RefreshScope
@Api(value="commonPost",tags = "普通帖子")
public class CommonPostController {
    @Autowired
    private CommonPostService commonPostService;

    @Autowired
    private HelpPostService helpPostService;

    @Autowired
    private MinIOService minIOService;

    @Autowired
    private UserService userService;

    @ApiOperation(value="用id查询帖子的详细信息")
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public Result<CommonPostDetail> CommonPostDetial(@RequestParam("id") String id)
    {
        CommonPost commonPost=commonPostService.getByID(id);
        if(commonPost==null){
            return Result.wrapErrorResult("不存在该帖子！");
        }
        CommonPostDetail commonPostDetail=new CommonPostDetail(commonPost);
        return Result.wrapSuccessfulResult(commonPostDetail);
    }

    @ApiOperation(value="查询满足指定条件的普通帖子的列表，若content为空，则返回所有帖子")
    @RequestMapping(value = "/briefList",method = RequestMethod.GET)
    public Result<List<PostBriefInfo>> CommonPostList(String content)
    {
        List<CommonPost> commonPosts= commonPostService.getAll();
        List<PostBriefInfo> posts=new ArrayList<>();
        for(CommonPost commonPost:commonPosts){
            PostBriefInfo postBriefInfo=new PostBriefInfo(commonPost);
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

    @ApiOperation(value="查询某个用户的普通帖子的列表")
    @RequestMapping(value = "/userBriefList",method = RequestMethod.GET)
    public Result<List<PostBriefInfo>> UserCommonPostList(HttpServletRequest httpServletRequest)
    {
        //获取user
        User user= userService.getById(JwtUtil.getIDByToken(httpServletRequest));

        List<CommonPost> commonPosts= commonPostService.findAllByUserId(user);

        List<PostBriefInfo> posts=new ArrayList<>();
        for(CommonPost commonPost:commonPosts){
            PostBriefInfo postBriefInfo=new PostBriefInfo(commonPost);
            posts.add(postBriefInfo);
        }

        return Result.wrapSuccessfulResult(posts);

    }

    @NeedToken(role = Role.both)
    @ApiOperation(value="用id删除指定帖子")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public Result<String> DeleteCommonPost(String id)
    {
        CommonPost commonPost=commonPostService.getByID(id);
        if(id==null){
            return Result.wrapErrorResult("该帖子不存在");
        }
        //先从minIO中删除对应图片
        if(commonPost.getPhotoOne()!=null){
            String[] url = commonPost.getPhotoOne().split("/");
            String file=url[url.length-1];
            minIOService.removeFile(file);
        }
        if(commonPost.getPhotoTwo()!=null){
            String[] url = commonPost.getPhotoTwo().split("/");
            String file=url[url.length-1];
            minIOService.removeFile(file);
        }
        if(commonPost.getPhotoThree()!=null){
            String[] url = commonPost.getPhotoThree().split("/");
            String file=url[url.length-1];
            minIOService.removeFile(file);
        }

        commonPostService.deleteByID(id);
        return Result.wrapSuccessfulResult("删除成功");
    }

    @NeedToken(role = Role.NormalUser)
    @ApiOperation(value="创建单个普通帖子")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public Result<String> CreateCommonPost(@RequestBody CommonPostCreateInfo commonPostCreateInfo,
                                           HttpServletRequest httpServletRequest)
    {
        //获取user
        User user= userService.getById(JwtUtil.getIDByToken(httpServletRequest));

        CommonPost commonPost=new CommonPost();
        commonPost.setId(commonPostService.generateID());
        commonPost.setUser(user);
        commonPost.setTime(java.time.Instant.now());
        commonPost.setThumbsUp(0);
        commonPost.setContent(commonPostCreateInfo.getContent());
        commonPost.setStatus(PostState.posted.toString());
        commonPost.setTitle(commonPostCreateInfo.getTitle());
        //最多存储三张图片
        if(commonPostCreateInfo.getPhotos().length>=1){
            commonPost.setPhotoOne(commonPostCreateInfo.getPhotos()[0]);
        }
        if(commonPostCreateInfo.getPhotos().length>=2){
            commonPost.setPhotoTwo(commonPostCreateInfo.getPhotos()[1]);
        }
        if(commonPostCreateInfo.getPhotos().length>=3){
            commonPost.setPhotoThree(commonPostCreateInfo.getPhotos()[2]);
        }

        commonPostService.createOrUpdate(commonPost);
        return Result.wrapSuccessfulResult("创建成功");
    }

    @NeedToken(role = Role.both)
    @ApiOperation(value="修改单个帖子，其中id为欲修改的帖子编号")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result<String> UpdateCommonPost(@RequestBody CommonPostUpdateInfo commonPostUpdateInfo,
                                           HttpServletRequest httpServletRequest)
    {
        CommonPost commonPost=commonPostService.getByID( commonPostUpdateInfo.getId());
        if(commonPost==null){
            return Result.wrapErrorResult("不存在该帖子");
        }
        //先从minIO中删除对应图片
        if(commonPostUpdateInfo.getPhotos().length>0){
            if(commonPost.getPhotoOne()!=null){
                String[] url = commonPost.getPhotoOne().split("/");
                String file=url[url.length-1];
                minIOService.removeFile(file);
            }
            if(commonPost.getPhotoTwo()!=null){
                String[] url = commonPost.getPhotoTwo().split("/");
                String file=url[url.length-1];
                minIOService.removeFile(file);
            }
            if(commonPost.getPhotoThree()!=null){
                String[] url = commonPost.getPhotoThree().split("/");
                String file=url[url.length-1];
                minIOService.removeFile(file);
            }
        }
        if(commonPostUpdateInfo.content!=null){
            commonPost.setContent(commonPostUpdateInfo.content);
        }
        if(commonPostUpdateInfo.title!=null){
            commonPost.setTitle(commonPostUpdateInfo.title);
        }
        commonPostService.createOrUpdate(commonPost);

        return Result.wrapSuccessfulResult("修改成功");
    }

    @NeedToken(role = Role.both)
    @ApiOperation(value="查询满足指定条件的所有帖子(普通和求助)的列表，若content为空，则返回所有帖子")
    @RequestMapping(value = "/allPostList",method = RequestMethod.GET)
    public Result<List<PostBriefInfo>> AllPostList(String content)
    {
        //普通
        List<CommonPost> commonPosts= commonPostService.getAll();
        List<PostBriefInfo> posts=new ArrayList<>();
        for(CommonPost commonPost:commonPosts){
            PostBriefInfo postBriefInfo=new PostBriefInfo(commonPost);
            posts.add(postBriefInfo);
        }

        //求助
        List<HelpPost> helpPosts= helpPostService.getAll();
        for(HelpPost helpPost:helpPosts){
            PostBriefInfo postBriefInfo=new PostBriefInfo(helpPost);
            posts.add(postBriefInfo);
        }

        if(content==null){
            Collections.sort(posts);
            return Result.wrapSuccessfulResult(posts);
        }
        else{
            List<PostBriefInfo> resultPosts=new ArrayList<>();
            for(PostBriefInfo postBriefInfo:posts){
                if(postBriefInfo.getContent().contains(content)){
                    resultPosts.add(postBriefInfo);
                }
            }
            Collections.sort(posts);
            return Result.wrapSuccessfulResult(resultPosts);
        }
    }

    @NeedToken(role = Role.Institution)
    @ApiOperation(value="机构获取所有被举报的帖子")
    @RequestMapping(value = "/reportedList",method = RequestMethod.GET)
    public Result<List<PostBriefInfo>> ReportedtList()
    {

        List<CommonPost> commonPosts= commonPostService.findAllByStatus(PostState.reported.toString());

        List<PostBriefInfo> posts=new ArrayList<>();
        for(CommonPost commonPost:commonPosts){
            PostBriefInfo postBriefInfo=new PostBriefInfo(commonPost);
            posts.add(postBriefInfo);
        }

        List<HelpPost> helpPosts= helpPostService.getAllByStatus(PostState.reported.toString());
        for(HelpPost helpPost:helpPosts){
            PostBriefInfo postBriefInfo=new PostBriefInfo(helpPost);
            posts.add(postBriefInfo);
        }

        return Result.wrapSuccessfulResult(posts);

    }

    static public class PostUpdateInfo{
        public int state;
        public String postID;
    }

    @NeedToken(role = Role.Institution)
    @ApiOperation(value="机构修改对应帖子的状态")
    @RequestMapping(value = "/updateState",method = RequestMethod.POST)
    public Result<String> UpdateState(@RequestBody PostUpdateInfo postUpdateInfo)
    {
        if(postUpdateInfo.postID.charAt(0) == 'C'){
            CommonPost commonPost=commonPostService.getByID(postUpdateInfo.postID);
            if(postUpdateInfo.state == 0){
                commonPost.setStatus(PostState.failed.toString());
                User user=userService.getById(commonPost.getUser().getId());
                user.setCredits(user.getCredits()-5);//扣除5分
                userService.createOrUpdate(user);
            }
            if(postUpdateInfo.state== 1){
                commonPost.setStatus(PostState.posted.toString());
            }
            commonPostService.createOrUpdate(commonPost);
        }

        if(postUpdateInfo.postID.charAt(0) == 'H'){
            HelpPost helpPost=helpPostService.getByID(postUpdateInfo.postID);
            if(postUpdateInfo.state == 0){
                helpPost.setStatus(PostState.failed.toString());
                User user=userService.getById(helpPost.getUser().getId());
                user.setCredits(user.getCredits()-5);//扣除5分
                userService.createOrUpdate(user);
            }
            if(postUpdateInfo.state== 1){
                helpPost.setStatus(PostState.posted.toString());
            }
            helpPostService.createOrUpdate(helpPost);
        }

        return Result.wrapSuccessfulResult("修改成功！");

    }

}
