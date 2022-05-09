package com.pet.userManage.controller;

import com.pet.login.service.UserService;
import com.pet.models.User;
import com.pet.userManage.entity.UserInfo;
import com.pet.util.config.NeedToken;
import com.pet.util.enums.Role;
import com.pet.util.utils.Encryption;
import com.pet.util.utils.JwtUtil;
import com.pet.util.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@CrossOrigin("*")
@RestController
@RequestMapping("api/user/info")
@RefreshScope
@Api(value="info",tags = "管理用户信息")
public class UserInfoController {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @NeedToken(role = Role.NormalUser)
    @ApiOperation(value = "删除用户")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public Result<String> DeleteUser(HttpServletRequest httpServletRequest){
        String userID = JwtUtil.getIDByToken(httpServletRequest);
        Boolean successful = userService.deleteById(userID);
        if(!successful){
            return Result.wrapErrorResult("Error!");
        }
        return Result.wrapSuccessfulResult("Success!");
    }

    @NeedToken(role = Role.NormalUser)
    @ApiOperation(value = "查询用户的详细信息")
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public Result<User> UserDetail(HttpServletRequest httpServletRequest){
        String userID = JwtUtil.getIDByToken(httpServletRequest);
        User user = userService.getById(userID);
        if(user==null){
            return Result.wrapErrorResult("该用户不存在");
        }
        //敏感信息置空
        user.setSalt(null);
        user.setPassword(null);
        return Result.wrapSuccessfulResult(user);
    }

    @NeedToken(role = Role.NormalUser)
    @ApiOperation(value = "修改用户的详细信息")
    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    public Result<String> ModifyUser(@RequestBody UserInfo info, HttpServletRequest httpServletRequest) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        String userID = JwtUtil.getIDByToken(httpServletRequest);
        User sessionUser = userService.getById(userID);
        Field[] field = info.getClass().getDeclaredFields();
        for (Field value : field) {
            String name = value.getName();
            if (name.equals("id") || name.equals("password") || name.equals("credits")) {
                continue;
            }
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            Method get = info.getClass().getMethod("get" + name);
            String type = value.getGenericType().toString().substring(6);
            Method set = sessionUser.getClass().getMethod("set" + name, Class.forName(type));
            if (get.invoke(info) != null) {
                set.invoke(sessionUser, get.invoke(info));
            }
        }
        userService.modifyById(sessionUser);
        return Result.wrapSuccessfulResult("Success!");
    }

    static public class PwdInfo{
        public String oldPwd;

        public String newPwd;
    }

    @NeedToken(role = Role.NormalUser)
    @ApiOperation(value = "修改用户的密码")
    @RequestMapping(value = "/modify/password",method = RequestMethod.POST)
    public Result<String> ModifyPassword(@RequestBody PwdInfo pwdInfo,
                                 HttpServletRequest httpServletRequest){
        String userID = JwtUtil.getIDByToken(httpServletRequest);
        User user=userService.getById(userID);
        if(!user.getPassword().equals(Encryption.shiroEncryption(pwdInfo.oldPwd,user.getSalt()))){
            return Result.wrapErrorResult("密码错误！");
        }
        user.setPassword(Encryption.shiroEncryption(pwdInfo.newPwd,user.getSalt()));
        userService.modifyById(user);
        return Result.wrapSuccessfulResult("Success!");
    }
}
