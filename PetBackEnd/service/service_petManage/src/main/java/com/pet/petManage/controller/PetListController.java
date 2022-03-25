package com.pet.petManage.controller;

import com.pet.models.Pet;
import com.pet.petManage.entity.BriefPetInfoReturn;
import com.pet.petManage.service.PetAdoptableListService;
import com.pet.petManage.service.PetLostListService;
import com.pet.petManage.service.PetManageService;
import com.pet.util.config.NeedToken;
import com.pet.util.enums.Role;
import com.pet.util.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/pet/info/list")
@RefreshScope
@Api(value="get_pet_list",tags = "查询宠物列表")
public class PetListController {
    @Autowired
    private PetAdoptableListService petAdoptableListService;

    @Autowired
    private PetLostListService petLostListService;

    @Autowired
    private PetManageService petManageService;

    @ApiOperation(value="查询待领养的宠物列表")
    @RequestMapping(value = "/adoptable",method = RequestMethod.GET)
    public Result<List<BriefPetInfoReturn>> PetAdoptable(@RequestParam(value = "type",required = false) String type,
                                                         @RequestParam(value = "lower_age",required = false) Integer lower_age,
                                                         @RequestParam(value = "upper_age",required = false) Integer upper_age,
                                                         @RequestParam(value = "sex",required = false) Boolean sex,
                                                         @RequestParam(value = "color",required = false) String color)
    {
        return getListResult(type, lower_age, upper_age, sex, color, petAdoptableListService.findByAdoptState());
    }

    @ApiOperation(value="查询待寻回的宠物列表")
    @RequestMapping(value = "/lost",method = RequestMethod.GET)
    public Result<List<BriefPetInfoReturn>> PetLost(@RequestParam(value = "type",required = false) String type,
                                                    @RequestParam(value = "lower_age",required = false) Integer lower_age,
                                                    @RequestParam(value = "upper_age",required = false) Integer upper_age,
                                                    @RequestParam(value = "sex",required = false) Boolean sex,
                                                    @RequestParam(value = "color",required = false) String color)
    {
        return getListResult(type, lower_age, upper_age, sex, color, petLostListService.findByAdoptState());
    }

    @NeedToken(role = Role.Institution)
    @ApiOperation(value="机构查询宠物列表")
    @RequestMapping(value = "/petList",method = RequestMethod.GET)
    public Result<List<BriefPetInfoReturn>> PetList(@RequestParam(value = "type",required = false) String type,
                                                    @RequestParam(value = "lower_age",required = false) Integer lower_age,
                                                    @RequestParam(value = "upper_age",required = false) Integer upper_age,
                                                    @RequestParam(value = "sex",required = false) Boolean sex,
                                                    @RequestParam(value = "color",required = false) String color,
                                                    @RequestParam(value = "status" ,defaultValue = "7",required = false) int status)
    {

        return getListResult(type, lower_age, upper_age, sex, color, petManageService.getByState(status));
    }

    private Result<List<BriefPetInfoReturn>> getListResult(String type, Integer lower_age, Integer upper_age, Boolean sex, String color, List<Pet> byAdoptState) {
        List<Pet> pet= byAdoptState;
        List<BriefPetInfoReturn> returnPetList = new ArrayList<>();
        if(pet.isEmpty())
            return Result.wrapErrorResult("不存在该类型的宠物！");
        for(Pet p:pet)
        {
            if(type!=null && !type.equals(p.getType()))
                continue;
            else if(sex!=null && sex!=p.getSex())
                continue;
            else if(color!=null && !color.equals(p.getColor()))
                continue;
            else if (lower_age!=null && p.getAge()<lower_age )
                continue;
            else if(upper_age!=null && p.getAge()>upper_age)
                continue;
            BriefPetInfoReturn returnPet = new BriefPetInfoReturn(p.getName(), p.getType(), p.getAge(),
                                                                  p.getPhoto(),p.getId(),p.getAdoptState());
            returnPetList.add(returnPet);
        }
        if(returnPetList.isEmpty()){
            return Result.wrapErrorResult("不存在该类型的宠物！");
        }
        else {
            return Result.wrapSuccessfulResult(returnPetList);
        }
    }


}

