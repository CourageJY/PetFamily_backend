package com.pet.application.controller;

import com.pet.application.service.*;
import com.pet.login.service.UserService;
import com.pet.minio.service.MinIOService;
import com.pet.models.*;
import com.pet.util.config.NeedToken;
import com.pet.util.enums.ApplicationState;
import com.pet.util.enums.Role;
import com.pet.util.utils.JwtUtil;
import com.pet.util.utils.Result;
import com.pet.util.utils.TimeConvert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/application/create")
@RefreshScope
@Api(value="application",tags = "创建申请表")
public class CreateApplicationController {
    @Autowired
    private AdoptApplicationService adoptApplicationService;

    @Autowired
    private SendApplicationService sendApplicationService;

    @Autowired
    private FindApplicationService findApplicationService;

    @Autowired
    private ReportApplicationService reportApplicationService;

    @Autowired
    private  PetService petService;

    @Autowired
    private UserService userService;

//    @Autowired
//    private MinIOService minIOService;

    private Long plusTime=28800L;//设置8个小时的秒数

    static public class AdoptRequest{
        public String  petID;
        public String  title;
        public String  economicCondition;
        public String  reason;
        public String  area;
        public String  phoneNum;
    }

    @NeedToken(role = Role.NormalUser)
    @ApiOperation(value="创建领养申请表， petID为欲领养的宠物ID，  \n" +
                        "title为申请表标题，economicCondition 为经济情况(前端做成几个选项)，  \n" +
                        "reason 为领养原因(不超过500字)，   \n" +
                        "area 为领养人所在的城市，phoneNum为领养人的电话号码")
    @RequestMapping(value = "/adoptApplication",method = RequestMethod.POST)
    public Result<String> createAdoptApplication(@RequestBody AdoptRequest adoptRequest,
                                                 HttpServletRequest httpServletRequest){
        //约束判断
        Pet pet =petService.findPetByID(adoptRequest.petID);
        if(pet==null){
            return Result.wrapErrorResult("该宠物不存在");
        }
        User user=userService.getById(JwtUtil.getIDByToken(httpServletRequest));
        if(user==null){
            return Result.wrapErrorResult("该用户不存在");
        }

        //填入属性
        AdoptApplication adoptApplication=new AdoptApplication();
        adoptApplication.setUser(user);
        adoptApplication.setId(adoptApplicationService.generateID());
        adoptApplication.setTitle(adoptRequest.title);
        adoptApplication.setTime(TimeConvert.getNowTime());
        adoptApplication.setPet(pet);
        adoptApplication.setEconomicCondition(adoptRequest.economicCondition);
        adoptApplication.setReason(adoptRequest.reason);
        adoptApplication.setStatus(ApplicationState.unchecked.toString());
        adoptApplication.setArea(adoptRequest.area);
        adoptApplication.setPhoneNum(adoptRequest.phoneNum);

        //存储
        adoptApplicationService.create(adoptApplication);

        return Result.wrapSuccessfulResult("创建成功");
    }

    static public class SendRequest{
        public String  certificate;
        public String  petPhoto;
        public String  title;
        public String  location;
        public String  reason;
        public String  sendTime;
        public String  phoneNum;
    }

    @NeedToken(role = Role.NormalUser)
    @ApiOperation(value="创建送养申请表，certificate为证书照片，petPhoto为宠物照片，   \n" +
                        "title为申请表标题，location为送养人的地址,reason为送养原因，   \n" +
                        "sendTime为送养时间，格式为'yyyy-MM-dd'，  \n" +
                        "phoneNum为电话号码，   \n" +
                        "宠物ID暂时为空，不需要传入")
    @RequestMapping(value = "/sendApplication",method = RequestMethod.POST)
    public Result<String> createSendApplication(@RequestBody SendRequest sendRequest,
                                                HttpServletRequest httpServletRequest){
        //约束判断
        User user=userService.getById(JwtUtil.getIDByToken(httpServletRequest));
        if(user==null){
            return Result.wrapErrorResult("该用户不存在");
        }

//        //在minIO中传入图片，并返回url
//        String certificateUrl= minIOService.uploadFile(sendRequest.certificate);
//        String petPhotoUrl= minIOService.uploadFile(sendRequest.petPhoto);

        //填入属性(宠物ID暂为空)
        SendApplication sendApplication=new SendApplication();
        sendApplication.setUser(user);
        sendApplication.setId(sendApplicationService.generateID());
        sendApplication.setTitle(sendRequest.title);
        sendApplication.setTime(TimeConvert.getNowTime());
        sendApplication.setLocation(sendRequest.location);
        sendApplication.setReason(sendRequest.reason);
        sendApplication.setSendTime(TimeConvert.convertStringToLocalDate(sendRequest.sendTime));
        sendApplication.setCertificate(sendRequest.certificate);//在mysql中存入url
        sendApplication.setPetPhoto(sendRequest.petPhoto);
        sendApplication.setPhoneNum(sendRequest.phoneNum);
        sendApplication.setStatus(ApplicationState.unchecked.toString());

        //存储
        sendApplicationService.create(sendApplication);

        return Result.wrapSuccessfulResult("创建成功");
    }

    static public class FindRequst{
        public String certificate;
        public String petPhoto;
        public String title;
        public String petID;
        public String description;
        public String phoneNum;
        public String area;

    }

    @NeedToken(role = Role.NormalUser)
    @ApiOperation(value=" 创建寻回申请表，certificate 为失主所有宠物的证书照片，petPhoto为失主曾经的对该宠物的照片,   \n" +
                        " title为申请表标题，  \n" +
                        " petID为宠物ID，description为寻回申请的具体描述，  \n" +
                        " phoneNum为电话号码")
    @RequestMapping(value = "/findApplication",method = RequestMethod.POST)
    public Result<String> createFindApplication(@RequestBody FindRequst findRequst,
                                                HttpServletRequest httpServletRequest){
        //约束判断
        User user=userService.getById(JwtUtil.getIDByToken(httpServletRequest));
        if(user==null){
            return Result.wrapErrorResult("该用户不存在");
        }

        Pet pet=petService.findPetByID(findRequst.petID);
        if(pet==null){
            return  Result.wrapErrorResult("该宠物不存在");
        }

//        //在minIO中传入图片，并返回url
//        String certificateUrl= minIOService.uploadFile(certificate);//证书图片
//        String petPhotoUrl=minIOService.uploadFile(petPhoto);//宠物图片

        //填入属性(宠物ID暂为空)
        FindApplication findApplication=new FindApplication();
        findApplication.setUser(user);
        findApplication.setId(findApplicationService.generateID());
        findApplication.setTitle(findRequst.title);
        findApplication.setTime(TimeConvert.getNowTime());//设置时间
        findApplication.setPet(pet);
        findApplication.setDescription(findRequst.description);
        findApplication.setCertificate(findRequst.certificate);//在mysql中存入url
        findApplication.setPetPhoto(findRequst.petPhoto);
        findApplication.setPhoneNum(findRequst.phoneNum);
        findApplication.setStatus(ApplicationState.unchecked.toString());

        //存储
        findApplicationService.create(findApplication);

        return Result.wrapSuccessfulResult("创建成功");
    }

    static public class ReportRequst{
        public String photoOne;
        public String photoTwo;
        public String title;
        public String description;
        public String phoneNum;
        public double locationX;
        public double locationY;

    }

    @NeedToken(role = Role.NormalUser)
    @ApiOperation(value="创建上报申请表，photoOne 和 photoTwo为与上报宠物相关的两张图片,   \n" +
                        "title为申请表标题，须由用户填写并返回给后端，   \n" +
                        "locationX为宠物所在的经度，locationY为宠物所在的纬度，   \n" +
                        "description为上报申请的详细信息，petID不需传入，" +
                        "phoneNum为电话号码")
    @RequestMapping(value = "/reportApplication",method = RequestMethod.POST)
    public Result<String> createReportApplication(@RequestBody ReportRequst reportRequst,
                                                  HttpServletRequest httpServletRequest){
        //约束判断
        User user=userService.getById(JwtUtil.getIDByToken(httpServletRequest));
        if(user==null){
            return Result.wrapErrorResult("该用户不存在");
        }

//        //在minIO中传入图片，并返回url
//        String photo_one= minIOService.uploadFile(photoOne);//证书图片
//        String photo_two=minIOService.uploadFile(photoTwo);//宠物图片

        //填入属性(宠物ID暂为空)
        ReportApplication reportApplication=new ReportApplication();
        reportApplication.setUser(user);
        reportApplication.setId(reportApplicationService.generateID());
        reportApplication.setTitle(reportRequst.title);
        reportApplication.setTime(TimeConvert.getNowTime());//设置时间
        reportApplication.setLocationX(reportRequst.locationX);
        reportApplication.setLocationY(reportRequst.locationY);
        reportApplication.setPhotoOne(reportRequst.photoOne);
        reportApplication.setPhotoTwo(reportRequst.photoTwo);
        reportApplication.setDescription(reportRequst.description);
        reportApplication.setPhoneNum(reportRequst.phoneNum);
        reportApplication.setStatus(ApplicationState.unchecked.toString());

        //存储
        reportApplicationService.create(reportApplication);

        return Result.wrapSuccessfulResult("创建成功");
    }
}
