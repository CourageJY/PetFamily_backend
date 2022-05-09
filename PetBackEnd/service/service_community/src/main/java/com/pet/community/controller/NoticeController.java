package com.pet.community.controller;

import com.pet.community.entity.NoticeCreateInfo;
import com.pet.community.entity.NoticeUpdateInfo;
import com.pet.community.service.NoticeService;
import com.pet.minio.service.MinIOService;
import com.pet.models.Notice;
import com.pet.util.config.NeedToken;
import com.pet.util.enums.Role;
import com.pet.util.utils.Result;
import com.pet.util.utils.TimeConvert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/community/notice")
@RefreshScope
@Api(value="notice",tags = "公告管理")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private MinIOService minIOService;

    @ApiOperation(value="用id查询公告的详细信息")
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public Result<Notice> NoticeDetial(@RequestParam("noticeID") String noticeID)
    {
        Notice notice=noticeService.getByID(noticeID);
        if(notice==null){
            return Result.wrapErrorResult("不存在该公告！");
        }
        return Result.wrapSuccessfulResult(notice);
    }

    @ApiOperation(value="查询所有公告列表")
    @RequestMapping(value = "/briefList",method = RequestMethod.GET)
    public Result<List<Notice>> NoticeList()
    {
        List<Notice> notices= noticeService.getAll();
        return Result.wrapSuccessfulResult(notices);
    }

    @NeedToken(role = Role.Institution)
    @ApiOperation(value="用id删除指定公告")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public Result<String> DeleteNotice(String id)
    {
        Notice notice=noticeService.getByID(id);
        if(id==null){
            return Result.wrapErrorResult("该公告不存在");
        }
        //先从minIO中删除对应图片
        String[] url = notice.getImage().split("/");
        String file=url[url.length-1];
        minIOService.removeFile(file);

        noticeService.deleteByID(id);
        return Result.wrapSuccessfulResult("删除成功");
    }

    @NeedToken(role = Role.Institution)
    @ApiOperation(value="创建单个公告")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public Result<String> CreateNotice(@RequestBody NoticeCreateInfo noticeCreateInfo)
    {
        Notice notice=new Notice();
        notice.setContext(noticeCreateInfo.getContext());
        notice.setImage(noticeCreateInfo.getImage());
        notice.setTitle(noticeCreateInfo.getTitle());
        notice.setId(noticeService.generateID());
        notice.setTime(TimeConvert.getNowTime());

        noticeService.createOrUpdate(notice);
        return Result.wrapSuccessfulResult("创建成功");
    }

    @NeedToken(role = Role.Institution)
    @ApiOperation(value="修改单个公告，其中id为欲修改的公告编号")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result<String> UpdateNotice(@RequestBody NoticeUpdateInfo noticeUpdateInfoInfo)
    {
        Notice notice=noticeService.getByID( noticeUpdateInfoInfo.getId());
        if(notice==null){
            return Result.wrapErrorResult("不存在该公告");
        }
        if(noticeUpdateInfoInfo.image!=null){
            //先从minIO中删除对应图片
            String[] url = notice.getImage().split("/");
            String file=url[url.length-1];
            minIOService.removeFile(file);

            notice.setImage(noticeUpdateInfoInfo.image);
        }
        if(noticeUpdateInfoInfo.context!=null){
            notice.setContext(noticeUpdateInfoInfo.context);
        }
        if(noticeUpdateInfoInfo.title!=null){
            notice.setTitle(noticeUpdateInfoInfo.title);
        }
        noticeService.createOrUpdate(notice);

        return Result.wrapSuccessfulResult("修改成功");
    }

}


