package com.pet.logistics.controller;

import io.swagger.annotations.Api;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("api/logistics/user")
@RefreshScope
@Api(value="logisticsUser",tags = "物流用户方面的接口")
public class UserLogisticsController {

}
