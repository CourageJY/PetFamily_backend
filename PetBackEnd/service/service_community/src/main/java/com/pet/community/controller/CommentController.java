package com.pet.community.controller;

import com.pet.community.entity.CommentCreateInfo;
import com.pet.community.entity.CommentReturnInfo;
import com.pet.community.repository.CommonPostRepository;
import com.pet.community.repository.HelpPostRepository;
import com.pet.community.service.CommentService;
import com.pet.community.service.CommonPostService;
import com.pet.community.service.HelpPostService;
import com.pet.login.service.UserService;
import com.pet.models.Comment;
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
@RequestMapping("api/comment")
@RefreshScope
@Api(value="comment",tags = "评论管理")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private HelpPostRepository helpPostRepository;

    @Autowired
    private CommonPostRepository commonPostRepository;

    @ApiOperation(value="用id查询评论的详细信息")
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public Result<CommentReturnInfo> CommentDetial(@RequestParam("id") String id)
    {
        Comment comment=commentService.getByID(id);
        if(comment==null){
            return Result.wrapErrorResult("不存在该评论！");
        }
        CommentReturnInfo commentReturnInfo=new CommentReturnInfo(comment);
        return Result.wrapSuccessfulResult(commentReturnInfo);
    }

    @ApiOperation(value="查询所有帖子的评论列表")
    @RequestMapping(value = "/postCommentList",method = RequestMethod.GET)
    public Result<List<CommentReturnInfo>> PostCommentList(String postId)
    {
        List<Comment> comments= commentService.getAllByPostId(postId);

        List<CommentReturnInfo> commentReturnInfos=new ArrayList<>();
        for(Comment comment:comments){
            CommentReturnInfo commentReturnInfo=new CommentReturnInfo(comment);
            commentReturnInfos.add(commentReturnInfo);
        }
        return Result.wrapSuccessfulResult(commentReturnInfos);
    }

    @NeedToken(role = Role.both)
    @ApiOperation(value="用id删除指定评论")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public Result<String> DeleteComment(String id)
    {
        Comment comment=commentService.getByID(id);
        if(id==null){
            return Result.wrapErrorResult("该公告不存在");
        }

        commentService.deleteByID(id);
        return Result.wrapSuccessfulResult("删除成功");
    }

    @NeedToken(role = Role.NormalUser)
    @ApiOperation(value="创建单个评论")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public Result<String> CreateComment(@RequestBody CommentCreateInfo commentCreateInfo,
                                        HttpServletRequest httpServletRequest)
    {
        if(!commonPostRepository.existsById(commentCreateInfo.postId)&&!helpPostRepository.existsById(commentCreateInfo.postId)){
            return Result.wrapErrorResult("该帖子不存在！");
        }
        Comment comment=new Comment();
        comment.setCommentId(commentService.generateID());
        comment.setContent(commentCreateInfo.getContent());
        comment.setTime(TimeConvert.getNowTime());
        comment.setUser(userService.getById(JwtUtil.getIDByToken(httpServletRequest)));
        comment.setPostId(commentCreateInfo.getPostId());

        commentService.createOrUpdate(comment);
        return Result.wrapSuccessfulResult("创建成功");
    }

}