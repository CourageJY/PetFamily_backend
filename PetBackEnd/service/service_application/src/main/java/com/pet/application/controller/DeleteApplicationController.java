package com.pet.application.controller;

import com.pet.application.service.*;
import com.pet.minio.service.MinIOService;
import com.pet.models.*;
import com.pet.util.config.NeedToken;
import com.pet.util.enums.Role;
import com.pet.util.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/application/delete")
@RefreshScope
@Api(value="application",tags = "删除申请表")
public class DeleteApplicationController {
    @Autowired
    private AdoptApplicationService adoptApplicationService;

    @Autowired
    private SendApplicationService sendApplicationService;

    @Autowired
    private FindApplicationService findApplicationService;

    @Autowired
    private ReportApplicationService reportApplicationService;

    @Autowired
    private MinIOService minIOService;

    @NeedToken(role = Role.both)
    @ApiOperation(value="删除领养申请表，输入为申请表ID")
    @RequestMapping(value = "/adoptApplication",method = RequestMethod.DELETE)
    public Result<String> deleteAdoptApplication(@RequestParam("application_id") String applicationID){
        AdoptApplication adoptApplication=adoptApplicationService.findByID(applicationID);
        if(adoptApplication==null){
            return Result.wrapErrorResult("该申请表ID不存在");
        }

        //删除
        if(adoptApplicationService.deleteByID(applicationID)){
            return Result.wrapSuccessfulResult("删除成功");
        }
        else{
            return Result.wrapErrorResult("删除失败");
        }

    }

    @NeedToken(role = Role.both)
    @ApiOperation(value="删除送养申请表，输入为申请表ID")
    @RequestMapping(value = "/sendApplication",method = RequestMethod.DELETE)
    public Result<String> deleteSendApplication(@RequestParam("application_id") String applicationID){
        SendApplication sendApplication=sendApplicationService.findByID(applicationID);
        if(sendApplication==null){
            return Result.wrapErrorResult("该申请表ID不存在");//是否需要删除对应图片？
        }
        //先从minIO中删除对应图片
        if(sendApplication.getPetPhoto()!=null&& !Objects.equals(sendApplication.getPetPhoto(), "")){
            String[] url1 = sendApplication.getPetPhoto().split("/");
            String petPhoto=url1[url1.length-1];
            minIOService.removeFile(petPhoto);
        }
        if(sendApplication.getCertificate()!=null&&!Objects.equals(sendApplication.getCertificate(), "")){
            String[] url2 = sendApplication.getCertificate().split("/");
            String certificate=url2[url2.length-1];

            minIOService.removeFile(certificate);
        }

        //删除
        if(sendApplicationService.deleteByID(applicationID)){
            return Result.wrapSuccessfulResult("删除成功");
        }
        else{
            return Result.wrapErrorResult("删除失败");
        }
    }

    @NeedToken(role = Role.both)
    @ApiOperation(value="删除寻回申请表，输入为申请表ID")
    @RequestMapping(value = "/findApplication",method = RequestMethod.DELETE)
    public Result<String> deleteFindApplication(@RequestParam("application_id") String applicationID){
        FindApplication findApplication=findApplicationService.findByID(applicationID);
        if(findApplication==null){
            return Result.wrapErrorResult("该申请表ID不存在");
        }
        //先从minIO中删除对应图片
        if(findApplication.getPetPhoto()!=null&&!Objects.equals(findApplication.getPetPhoto(), "")){
            String[] url1 = findApplication.getPetPhoto().split("/");
            String petPhoto=url1[url1.length-1];
            minIOService.removeFile(petPhoto);
        }
        if(findApplication.getCertificate()!=null&&!Objects.equals(findApplication.getCertificate(), "")){
            String[] url2 = findApplication.getCertificate().split("/");
            String certificate=url2[url2.length-1];

            minIOService.removeFile(certificate);
        }
        //删除
        if(findApplicationService.deleteByID(applicationID)){
            return Result.wrapSuccessfulResult("删除成功");
        }
        else{
            return Result.wrapErrorResult("删除失败");
        }

    }

    @NeedToken(role = Role.both)
    @ApiOperation(value="删除上报申请表，输入为申请表ID")
    @RequestMapping(value = "/reportApplication",method = RequestMethod.DELETE)
    public Result<String> deleteReportApplication(@RequestParam("application_id") String applicationID){
        ReportApplication reportApplication=reportApplicationService.findByID(applicationID);
        if(reportApplication==null){
            return Result.wrapErrorResult("该申请表ID不存在");//是否需要删除对应图片？
        }
        //先从minIO中删除对应图片
        if(reportApplication.getPhotoOne()!=null&& !Objects.equals(reportApplication.getPhotoOne(), "")){
            String[] url1 = reportApplication.getPhotoOne().split("/");
            String photo1=url1[url1.length-1];
            minIOService.removeFile(photo1);
        }
        if(reportApplication.getPhotoTwo()!=null&& !Objects.equals(reportApplication.getPhotoOne(), "")){
            String[] url2 = reportApplication.getPhotoTwo().split("/");
            String photo2=url2[url2.length-1];
            minIOService.removeFile(photo2);
        }

        //删除
        if(reportApplicationService.deleteByID(applicationID)){
            return Result.wrapSuccessfulResult("删除成功");
        }
        else{
            return Result.wrapErrorResult("删除失败");
        }

    }

}
