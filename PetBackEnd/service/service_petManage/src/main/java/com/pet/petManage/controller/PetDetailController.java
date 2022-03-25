package com.pet.petManage.controller;

import com.pet.models.Pet;
import com.pet.petManage.service.PetDetailService;
import com.pet.util.config.NeedToken;
import com.pet.util.utils.JwtUtil;
import com.pet.util.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import com.pet.util.enums.Role;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin("*")
@RestController
@RequestMapping("api/pet/info")
@RefreshScope
@Api(value="get_detail",tags = "查询宠物的详细信息")
public class PetDetailController {
    @Autowired
    private PetDetailService petDetailService;

    @ApiOperation(value="查询宠物的详细信息")
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
//    @NeedToken(role = Role.NormalUser) //token权限认证
    public Result<Pet> PetDetail(@RequestParam("petID") String petID)
    {
        Pet pet=petDetailService.getById(petID);
        if(pet==null){
            return Result.wrapErrorResult("不存在该宠物！");
        }
//        System.out.println(JwtUtil.getIDByToken(httpServletRequest));//
        return Result.wrapSuccessfulResult(pet);
    }
}
