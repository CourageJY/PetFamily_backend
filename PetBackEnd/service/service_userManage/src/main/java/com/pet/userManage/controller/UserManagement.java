package com.pet.userManage.controller;

import com.pet.login.service.EmailService;
import com.pet.userManage.entity.EmailSendInfo;
import com.pet.util.config.NeedToken;
import com.pet.util.enums.Role;
import com.pet.util.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/userManagement")
@RefreshScope
@Api(value="userManagement",tags = "用户信息管理")
public class UserManagement {
    @Autowired
    EmailService emailService;

    @NeedToken(role = Role.Institution)
    @ApiOperation(value="发送指定内容的邮件")
    @RequestMapping(value = "/getCode",method = RequestMethod.POST)
    public Result<String> sendEmail(@RequestBody EmailSendInfo emailSendInfo){
        //邮件发送接口
        try{
            emailService.sendEmail(emailSendInfo.getEmail(),emailSendInfo.getTitle(),
                                      emailSendInfo.getContent());
        }catch (Exception e){
            return Result.wrapErrorResult(e.getMessage());
        }

        return Result.wrapSuccessfulResult("发送成功");
    }
}
