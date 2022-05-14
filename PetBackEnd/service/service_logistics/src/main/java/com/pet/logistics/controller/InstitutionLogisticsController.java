package com.pet.logistics.controller;

import io.swagger.annotations.Api;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("api/logistics/institution")
@RefreshScope
@Api(value="logisticsInstitution",tags = "物流机构方面的接口")
public class InstitutionLogisticsController {
}
