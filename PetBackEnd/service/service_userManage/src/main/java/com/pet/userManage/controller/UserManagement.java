package com.pet.userManage.controller;

import com.pet.login.service.EmailService;
import com.pet.login.service.UserService;
import com.pet.models.User;
import com.pet.userManage.entity.EmailSendInfo;
import com.pet.userManage.service.OrderInfoService;
import com.pet.userManage.service.PetService;
import com.pet.util.config.NeedToken;
import com.pet.util.enums.Role;
import com.pet.util.utils.JwtUtil;
import com.pet.util.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/user/management")
@RefreshScope
@Api(value="management",tags = "用户信息管理")
public class UserManagement {
    @Autowired
    EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private PetService petService;

    //@NeedToken(role = Role.Institution)
    @ApiOperation(value = "发送指定内容的邮件")
    @RequestMapping(value = "/getCode", method = RequestMethod.POST)
    public Result<String> sendEmail(@RequestBody EmailSendInfo emailSendInfo) {
        //邮件发送接口
        try {
            emailService.sendEmail(emailSendInfo.getEmail(), emailSendInfo.getTitle(),
                    emailSendInfo.getContent());
        } catch (Exception e) {
            return Result.wrapErrorResult(e.getMessage());
        }

        return Result.wrapSuccessfulResult("发送成功");
    }

    //@NeedToken(role = Role.Institution)
    @ApiOperation(value = "根据id获取指定用户信息")
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public Result<User> getUser(@RequestParam("userID") String userID) {
        User user = userService.getById(userID);
        if (user == null) {
            return Result.wrapErrorResult("该用户不存在");
        }
        //敏感信息置空
        user.setSalt(null);
        user.setPassword(null);
        return Result.wrapSuccessfulResult(user);
    }

    //@NeedToken(role = Role.Institution)
    @ApiOperation(value = "获取所有用户信息")
    @RequestMapping(value = "/getAllUser", method = RequestMethod.GET)
    public Result<List<User>> getAllUser() {
        List<User> users = userService.getAllUser();
        if (users == null) {
            return Result.wrapErrorResult("该用户不存在");
        }
        //敏感信息置空
        for (int i = 0; i < users.size(); i++) {
            users.get(i).setSalt(null);
            users.get(i).setPassword(null);
        }
        return Result.wrapSuccessfulResult(users);
    }

    //@NeedToken(role = Role.Institution)
    @ApiOperation(value = "将指定用户移入黑名单")
    @RequestMapping(value = "/setBlackList", method = RequestMethod.POST)
    public Result<String> setBlackList(@RequestParam("userID") String userID) {
        User user = userService.getById(userID);
        if (user == null) {
            return Result.wrapErrorResult("该用户不存在");
        }
        user.setBlacklist(1);//拉入黑名单
        userService.createOrUpdate(user);
        return Result.wrapSuccessfulResult("设置成功");
    }

    @ApiOperation(value = "获取用户数量")
    @RequestMapping(value = "/getUserCount", method = RequestMethod.GET)
    public Result<Integer> getUserCount() {
        List<User> users = userService.getAllUser();
        if (users == null) {
            return Result.wrapErrorResult("该用户不存在");
        }
        //敏感信息置空
        for (int i = 0; i < users.size(); i++) {
            users.get(i).setSalt(null);
            users.get(i).setPassword(null);
        }
        return Result.wrapSuccessfulResult(users.size());
    }

    @ApiOperation(value = "获取订单数量")
    @RequestMapping(value = "/getOrderCount", method = RequestMethod.GET)
    public Result<Integer> getOrderCount() {
        return Result.wrapSuccessfulResult(orderInfoService.getOrderCount());
    }

    @ApiOperation(value = "获取宠物数量")
    @RequestMapping(value = "/getPetCount", method = RequestMethod.GET)
    public Result<Integer> getPetCount() {
        return Result.wrapSuccessfulResult(petService.getPetCount());
    }

    @ApiOperation(value = "获取黑名单用户列表")
    @RequestMapping(value = "/getBlackListt", method = RequestMethod.GET)
    public Result<List<User>> getBlackList() {
        return Result.wrapSuccessfulResult(userService.getAllBlackUser());
    }

    @ApiOperation(value = "将用户移除黑名单")
    @RequestMapping(value = "/removeBlack", method = RequestMethod.GET)
    public Result<String> removeBlack(@RequestParam("userID") String userID) {
        User user = userService.getById(userID);
        if (user == null) {
            return Result.wrapErrorResult("该用户不存在");
        }
        user.setBlacklist(0);//移除黑名单
        userService.createOrUpdate(user);
        return Result.wrapSuccessfulResult("设置成功");
    }

    @ApiOperation(value = "修改用户积分")
    @RequestMapping(value = "/changeCredit", method = RequestMethod.POST)
    public Result<String> removeBlack(@RequestParam("userID") String userID,@RequestParam("credit") Integer credit) {
        User user = userService.getById(userID);
        if (user == null) {
            return Result.wrapErrorResult("该用户不存在");
        }
        user.setCredits(credit);//设置积分
        userService.createOrUpdate(user);
        return Result.wrapSuccessfulResult("积分设置成功");
    }

}


