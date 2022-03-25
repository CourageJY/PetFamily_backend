package com.pet.petManage.controller;

import com.pet.minio.service.MinIOService;
import com.pet.models.Pet;
import com.pet.models.User;
import com.pet.petManage.entity.AddPetRequest;
import com.pet.petManage.entity.PetUpdateInfo;
import com.pet.petManage.repository.PetRepository;
import com.pet.petManage.service.PetDetailService;
import com.pet.petManage.service.PetManageService;
import com.pet.util.config.NeedToken;
import com.pet.util.enums.PetState;
import com.pet.util.enums.Role;
import com.pet.util.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@CrossOrigin("*")
@RestController
@RequestMapping("api/pet/manage")
@RefreshScope
@Api(value="pet_manage",tags = "机构管理宠物")
public class PetManageController {
    @Autowired
    private PetManageService petManageService;

    @Autowired
    private PetDetailService petDetailService;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private MinIOService minIOService;

    @NeedToken(role = Role.Institution)
    @ApiOperation(value = "新建宠物")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result<Pet> addPet(@RequestBody AddPetRequest addPetRequest) {
        Boolean successful = petManageService.addPet(addPetRequest);
        if(!successful)
            return Result.wrapErrorResult("新增宠物信息失败！");
        else
            return Result.wrapSuccessfulResult("新增宠物信息成功！");
    }

    @NeedToken(role = Role.Institution)
    @ApiOperation(value = "删除宠物")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public Result<User> DeleteUser(@RequestParam("petID") String petID){
        Pet pet=petDetailService.getById(petID);
        if(pet==null){
            return Result.wrapErrorResult("该宠物不存在");
        }
        //删除对应图片
        String[] url=pet.getPhoto().split("/");
        String fileName=url[url.length-1];
        minIOService.removeFile(fileName);

        Boolean successful = petManageService.deleteById(petID);
        if(!successful){
            return Result.wrapErrorResult("删除失败!");
        }
        return Result.wrapSuccessfulResult("删除宠物信息成功!");
    }

    @NeedToken(role = Role.Institution)
    @ApiOperation(value = "机构修改宠物基本信息")
    @RequestMapping(value = "/UpdatePet",method = RequestMethod.POST)
    public Result<String> UpdatePet(@RequestBody PetUpdateInfo updateInfo){
        Pet pet=petDetailService.getById(updateInfo.petID);
        if(pet==null){
            return Result.wrapErrorResult("该宠物ID不存在！");
        }

        if(!Objects.equals(updateInfo.photo, null)){
            //删除原来的图片
            String[] url=updateInfo.photo.split("/");
            String fileName=url[url.length-1];
            minIOService.removeFile(fileName);
        }

        if(petManageService.update(updateInfo)){
            return Result.wrapSuccessfulResult("更新宠物信息成功!");
        }
        else{
            return Result.wrapErrorResult("更新宠物信息失败");
        }
    }

    @NeedToken(role = Role.Institution)
    @ApiOperation(value = "机构修改宠物状态,state 0:为待寻回；1：为待领养；2：为等待寻回；3：等待领养；4：已被寻回；5：已被领养；6：死亡")
    @RequestMapping(value = "/UpdatePetState",method = RequestMethod.POST)
    public Result<String> UpdatePetState(@RequestParam String petID,@RequestParam int state){
        Pet pet=petDetailService.getById(petID);
        if(pet==null){
            return Result.wrapErrorResult("该宠物不存在");
        }
        switch (state){
            case 0: pet.setAdoptState(PetState.canBeFoundBack.toString());break;
            case 1: pet.setAdoptState(PetState.canBeAdopted.toString());break;
            case 2: pet.setAdoptState(PetState.waitingFindback.toString());break;
            case 3: pet.setAdoptState(PetState.waitingAdopted.toString());break;
            case 4: pet.setAdoptState(PetState.findBack.toString());break;
            case 5: pet.setAdoptState(PetState.adopted.toString());break;
            case 6: pet.setAdoptState(PetState.dead.toString());break;
            default:return Result.wrapErrorResult("宠物状态输入不对，只能为数字0~6");
        }

        petRepository.save(pet);
        return Result.wrapSuccessfulResult("宠物状态修改成功");
    }
}
