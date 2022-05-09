package com.pet.login.controller;

import com.pet.login.entity.Code;
import com.pet.login.entity.FindPwd;
import com.pet.login.entity.LoginInfo;
import com.pet.login.service.EmailService;
import com.pet.login.service.InstitutionService;
import com.pet.login.service.UserService;
import com.pet.models.InstitutionWorker;
import com.pet.models.User;
import com.pet.util.utils.Encryption;
import com.pet.util.utils.JwtUtil;
import com.pet.util.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/loginRegister/login")
@RefreshScope
@Api(value="login",tags = "登录")
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private InstitutionService institutionService;

    @ApiOperation(value="用户登录")
    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public Result<String> userLogin(@RequestBody LoginInfo loginInfo)
    {
        User user=userService.getById(loginInfo.userID_or_email);
        if(user==null){
            user=userService.getByEmail(loginInfo.userID_or_email);
        }
        if(user==null){
            return Result.wrapErrorResult("不存在该用户名或邮箱号！");
        }
        if(!user.getPassword().equals(Encryption.shiroEncryption(loginInfo.password,user.getSalt()))){
            return Result.wrapErrorResult("密码错误！");
        }
        //产生token
        String token= JwtUtil.getToken(user);
        return Result.wrapSuccessfulResult("登录成功",token);
    }

    @ApiOperation(value = "机构登录")
    @RequestMapping(value = "/institution", method = RequestMethod.POST)
    public Result<String> institutionLogin(@RequestBody LoginInfo loginInfo) {
        InstitutionWorker institution=institutionService.getById(loginInfo.userID_or_email);
        if(institution==null){
            return Result.wrapErrorResult("不存在该用户名！");
        }
        if(!institution.getPassword().equals(Encryption.shiroEncryption(loginInfo.password,institution.getSalt()))){
            return Result.wrapErrorResult("密码错误！");
        }
        //产生token
        String token= JwtUtil.getToken(institution);
        return Result.wrapSuccessfulResult("登录成功",token);
    }

}
