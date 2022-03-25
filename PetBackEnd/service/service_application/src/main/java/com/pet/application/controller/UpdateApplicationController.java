package com.pet.application.controller;

import com.pet.application.entity.AdoptApplicationUpdate;
import com.pet.application.entity.FindApplicationUpdate;
import com.pet.application.entity.ReportApplicationUpdate;
import com.pet.application.entity.SendApplicationUpdate;
import com.pet.application.service.*;
import com.pet.login.service.UserService;
import com.pet.minio.service.MinIOService;
import com.pet.models.AdoptApplication;
import com.pet.models.FindApplication;
import com.pet.models.ReportApplication;
import com.pet.models.SendApplication;
import com.pet.util.config.NeedToken;
import com.pet.util.enums.ApplicationState;
import com.pet.util.enums.PetState;
import com.pet.util.enums.Role;
import com.pet.util.utils.Result;
import com.pet.util.utils.TimeConvert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/application/update")
@RefreshScope
@Api(value="application",tags = "更新申请表")
public class UpdateApplicationController {
    @Autowired
    private AdoptApplicationService adoptApplicationService;

    @Autowired
    private SendApplicationService sendApplicationService;

    @Autowired
    private FindApplicationService findApplicationService;

    @Autowired
    private ReportApplicationService reportApplicationService;

    @Autowired
    private PetService petService;

    @Autowired
    private MinIOService minIOService;

    /**
     * 机构人员的更新申请表状态
     */
    @NeedToken(role = Role.Institution)
    @ApiOperation(value="更新领养申请表状态，输入为申请表ID和审核结果（通过为1，不通过为0）")
    @RequestMapping(value = "/adoptApplicationState",method = RequestMethod.POST)
    public Result<String> updateAdoptApplicationState(@RequestParam("application_id") String applicationID,
                                                      @RequestParam("isApprove") int isApprove){
        AdoptApplication adoptApplication=adoptApplicationService.findByID(applicationID);
        if(adoptApplication==null){
            return Result.wrapErrorResult("该申请表ID不存在");
        }
        //修改申请表的状态
        AdoptApplicationUpdate adoptApplicationUpdate=new AdoptApplicationUpdate();
        if(isApprove==0){
            adoptApplicationUpdate.setStatus(ApplicationState.disapprove.toString());
        }
        else if(isApprove==1){
            adoptApplicationUpdate.setStatus(ApplicationState.approve.toString());
            //更新对应的宠物状态
            petService.updateState(adoptApplication.getPet().getId(), PetState.waitingAdopted);
        }
        else{
            return Result.wrapErrorResult("isApprove的值只能是0或1");
        }
        adoptApplicationUpdate.setUpdateTime();
        adoptApplicationUpdate.setApplicationID(applicationID);

        //更新申请表状态
        adoptApplicationService.Update(adoptApplicationUpdate);
        return Result.wrapSuccessfulResult("状态更新成功");

    }

    @NeedToken(role = Role.Institution)
    @ApiOperation(value="更新寻回申请表状态，输入为申请表ID和审核结果（通过为1，不通过为0）")
    @RequestMapping(value = "/findApplicationState",method = RequestMethod.POST)
    public Result<String> updateFindApplicationState(@RequestParam("application_id") String applicationID,
                                                     @RequestParam("isApprove") int isApprove){
        FindApplication findApplication=findApplicationService.findByID(applicationID);
        if(findApplication==null){
            return Result.wrapErrorResult("该申请表ID不存在");
        }
        //修改申请表的状态
        FindApplicationUpdate findApplicationUpdate=new FindApplicationUpdate();
        if(isApprove==0){
            findApplicationUpdate.setStatus(ApplicationState.disapprove.toString());
        }
        else if(isApprove==1){
            findApplicationUpdate.setStatus(ApplicationState.approve.toString());
            //更新对应的宠物状态
            petService.updateState(findApplication.getPet().getId(), PetState.waitingFindback);
        }
        else{
            return Result.wrapErrorResult("isApprove的值只能是0或1");
        }
        findApplicationUpdate.setUpdateTime();
        findApplicationUpdate.setApplicationID(applicationID);

        //更新申请表状态
        findApplicationService.Update(findApplicationUpdate);
        return Result.wrapSuccessfulResult("状态更新成功");

    }

    @NeedToken(role = Role.Institution)
    @ApiOperation(value="更新上报申请表状态，输入为申请表ID和审核结果（通过为1，不通过为0）")
    @RequestMapping(value = "/reportApplicationState",method = RequestMethod.POST)
    public Result<String> updateReportApplicationState(@RequestParam("application_id") String applicationID,
                                                       @RequestParam("isApprove") int isApprove){
        ReportApplication reportApplication=reportApplicationService.findByID(applicationID);
        if(reportApplication==null){
            return Result.wrapErrorResult("该申请表ID不存在");
        }
        //修改申请表的状态
        ReportApplicationUpdate reportApplicationUpdate=new ReportApplicationUpdate();
        if(isApprove==0){
            reportApplicationUpdate.setStatus(ApplicationState.disapprove.toString());
        }
        else if(isApprove==1){
            reportApplicationUpdate.setStatus(ApplicationState.approve.toString());
        }
        else{
            return Result.wrapErrorResult("isApprove的值只能是0或1");
        }
        reportApplicationUpdate.setUpdateTime();
        reportApplicationUpdate.setApplicationID(applicationID);

        //更新申请表状态
        reportApplicationService.Update(reportApplicationUpdate);
        return Result.wrapSuccessfulResult("状态更新成功");

    }

    @NeedToken(role = Role.Institution)
    @ApiOperation(value="更新送养申请表状态，输入为申请表ID和审核结果（通过为1，不通过为0）")
    @RequestMapping(value = "/sendApplicationState",method = RequestMethod.POST)
    public Result<String> updateSendApplicationState(@RequestParam("application_id") String applicationID,
                                                     @RequestParam("isApprove") int isApprove){
        SendApplication sendApplication=sendApplicationService.findByID(applicationID);
        if(sendApplication==null){
            return Result.wrapErrorResult("该申请表ID不存在");
        }
        //修改申请表的状态
        SendApplicationUpdate sendApplicationUpdate=new SendApplicationUpdate();
        if(isApprove==0){
            sendApplicationUpdate.setStatus(ApplicationState.disapprove.toString());
        }
        else if(isApprove==1){
            sendApplicationUpdate.setStatus(ApplicationState.approve.toString());
        }
        else{
            return Result.wrapErrorResult("isApprove的值只能是0或1");
        }
        sendApplicationUpdate.setApplicationID(applicationID);
        sendApplicationUpdate.setUpdateTime();

        //更新申请表状态
        sendApplicationService.Update(sendApplicationUpdate);
        return Result.wrapSuccessfulResult("状态更新成功");

    }

    /**
     * 注册用户更新申请表状态（）
     */
    static public class AdoptRequest{
        public String applicationID;
        public String title;
        public String economicCondition;
        public String reason;
        public String area;
        public String phoneNum;
    }

    @NeedToken(role = Role.NormalUser)
    @ApiOperation(value="更新领养申请表,不须更改的字符串设置为空")
    @RequestMapping(value = "/adoptApplicationUpdate",method = RequestMethod.POST)
    public Result<String> updateAdoptApplicationState(@RequestBody AdoptRequest adoptRequest){
        AdoptApplication application=adoptApplicationService.findByID(adoptRequest.applicationID);
        if(application==null){
            return Result.wrapErrorResult("该申请表不存在");
        }
        AdoptApplicationUpdate adoptApplicationUpdate=new AdoptApplicationUpdate();
        adoptApplicationUpdate.setApplicationID(application.getId());
        adoptApplicationUpdate.setTitle(adoptRequest.title);
        adoptApplicationUpdate.setEconomicCondition(adoptRequest.economicCondition);
        adoptApplicationUpdate.setReason(adoptRequest.reason);
        adoptApplicationUpdate.setPhoneNum(adoptRequest.phoneNum);
        adoptApplicationUpdate.setArea(adoptRequest.area);
        adoptApplicationUpdate.setUpdateTime();

        //更新申请表
        adoptApplicationService.Update(adoptApplicationUpdate);
        return Result.wrapSuccessfulResult("状态更新成功");

    }

    static public class SendRequest{
        public String applicationID;
        public String title;
        public String location;
        public String reason;
        public String certificateUrl;
        public String petPhotoUrl;
        public String phoneNum;
        public String sendTime;
    }

    @NeedToken(role = Role.NormalUser)
    @ApiOperation(value="更新送养申请表,不须更改的字符串设置为空,图片须传入返回的url")
    @RequestMapping(value = "/sendApplicationUpdate",method = RequestMethod.POST)
    public Result<String> updateSendApplicationState(@RequestBody SendRequest sendRequest){
        SendApplication application=sendApplicationService.findByID(sendRequest.applicationID);
        if(application==null){
            return Result.wrapErrorResult("该申请表不存在");
        }

        //从minIO中删除旧的图片
        if(!Objects.equals(sendRequest.petPhotoUrl, null)){
            String[] url1 = application.getPetPhoto().split("/");
            String petPhoto=url1[url1.length-1];
            minIOService.removeFile(petPhoto);
        }
        if(!Objects.equals(sendRequest.certificateUrl, null)){
            String[] url2 = application.getCertificate().split("/");
            String certificate=url2[url2.length-1];
            minIOService.removeFile(certificate);
        }

        SendApplicationUpdate sendApplicationUpdate=new SendApplicationUpdate();
        sendApplicationUpdate.setApplicationID(application.getId());
        sendApplicationUpdate.setTitle(sendRequest.title);
        sendApplicationUpdate.setLocation(sendRequest.location);
        sendApplicationUpdate.setReason(sendRequest.reason);
        sendApplicationUpdate.setPetPhoto(sendRequest.petPhotoUrl);
        sendApplicationUpdate.setCertificate(sendRequest.certificateUrl);
        sendApplicationUpdate.setPhoneNum(sendRequest.phoneNum);
        sendApplicationUpdate.setUpdateTime();
        sendApplicationUpdate.setSendTime(TimeConvert.convertStringToLocalDate(sendRequest.sendTime));

        //更新申请表
        sendApplicationService.Update(sendApplicationUpdate);
        return Result.wrapSuccessfulResult("状态更新成功");

    }

    static public class FindRequest{
        public String applicationID;
        public String title;
        public String description;
        public String certificateUrl;
        public String petPhotoUrl;
        public String phoneNum;
    }

    @NeedToken(role = Role.NormalUser)
    @ApiOperation(value="更新寻回申请表,不须更改的字符串设置为空,图片须传入返回的url")
    @RequestMapping(value = "/findApplicationUpdate",method = RequestMethod.POST)
    public Result<String> updateFindApplicationState(@RequestBody FindRequest findRequest){
        FindApplication application=findApplicationService.findByID(findRequest.applicationID);
        if(application==null){
            return Result.wrapErrorResult("该申请表不存在");
        }

        //从minIO中删除旧的图片
        if(!Objects.equals(findRequest.petPhotoUrl, null)){
            String[] url1 = application.getPetPhoto().split("/");
            String petPhoto=url1[url1.length-1];
            minIOService.removeFile(petPhoto);
        }
        if(!Objects.equals(findRequest.certificateUrl, null)){
            String[] url2 = application.getCertificate().split("/");
            String certificate=url2[url2.length-1];
            minIOService.removeFile(certificate);
        }

        FindApplicationUpdate findApplicationUpdate=new FindApplicationUpdate();
        findApplicationUpdate.setApplicationID(application.getId());
        findApplicationUpdate.setTitle(findRequest.title);
        findApplicationUpdate.setDescription(findRequest.description);
        findApplicationUpdate.setPetPhoto(findRequest.petPhotoUrl);
        findApplicationUpdate.setCertificate(findRequest.certificateUrl);
        findApplicationUpdate.setPhoneNum(findRequest.phoneNum);
        findApplicationUpdate.setUpdateTime();

        //更新申请表
        findApplicationService.Update(findApplicationUpdate);
        return Result.wrapSuccessfulResult("状态更新成功");

    }

    static public class ReportRequest{
        public String applicationID;
        public String title;
        public String description;
        public Double locationX;
        public Double LocationY;
        public String photoOne;
        public String photoTwo;
        public String phoneNum;
    }

    @NeedToken(role = Role.NormalUser)
    @ApiOperation(value="更新上报申请表，不须更改的字符串设置为空，位置不更改设置为0，图片须传入返回的url")
    @RequestMapping(value = "/reportApplicationUpdate",method = RequestMethod.POST)
    public Result<String> updateReportApplicationState(@RequestBody ReportRequest reportRequest){
        ReportApplication application=reportApplicationService.findByID(reportRequest.applicationID);
        if(application==null){
            return Result.wrapErrorResult("该申请表不存在");
        }

        //从minIO中删除旧的图片
        if(!Objects.equals(reportRequest.photoOne, null)){
            String[] url1 = application.getPhotoOne().split("/");
            String petPhoto=url1[url1.length-1];
            minIOService.removeFile(petPhoto);
        }
        if(!Objects.equals(reportRequest.photoTwo, null)){
            String[] url2 = application.getPhotoTwo().split("/");
            String certificate=url2[url2.length-1];
            minIOService.removeFile(certificate);
        }

        ReportApplicationUpdate reportApplicationUpdate=new ReportApplicationUpdate();
        reportApplicationUpdate.setApplicationID(application.getId());
        reportApplicationUpdate.setTitle(reportRequest.title);
        reportApplicationUpdate.setDescription(reportRequest.description);
        reportApplicationUpdate.setPhotoOne(reportRequest.photoOne);
        reportApplicationUpdate.setPhotoTwo(reportRequest.photoTwo);
        reportApplicationUpdate.setPhoneNum(reportRequest.phoneNum);
        reportApplicationUpdate.setUpdateTime();

        //更新申请表
        reportApplicationService.Update(reportApplicationUpdate);
        return Result.wrapSuccessfulResult("状态更新成功");

    }

}
