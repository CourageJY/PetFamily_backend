package com.pet.petManage.service;

import com.pet.minio.service.MinIOService;
import com.pet.models.Pet;
import com.pet.models.User;
import com.pet.petManage.entity.AddPetRequest;
import com.pet.petManage.entity.PetUpdateInfo;
import com.pet.petManage.repository.PetRepository;
import com.pet.petManage.repository.UserRepository;
import com.pet.util.enums.PetState;
import com.pet.util.utils.TimeConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class PetManageService {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    public Boolean addPet(AddPetRequest addPetRequest) {
        try {
            String id = generateID();
            Pet pet = new Pet(addPetRequest.getName(), addPetRequest.getType(),
                              addPetRequest.getAge(), addPetRequest.getSex(),
                              addPetRequest.getColor(), addPetRequest.getDescription(),
                              addPetRequest.getPhoto(), addPetRequest.getArea(),addPetRequest.getPrice());
            pet.setId(id);
            pet.setAdoptState(PetState.canBeFoundBack.toString());
            pet.setTime(LocalDate.now());//自动注册寻回到机构的日期

            petRepository.save(pet);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Boolean deleteById(String petID){
        try {
            petRepository.deleteById(petID);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<Pet> getByState(int state){
        String str=null;
        switch(state){
            case 0: str=PetState.canBeFoundBack.toString();break;
            case 1: str=PetState.canBeAdopted.toString();break;
            case 2: str=PetState.waitingFindback.toString();break;
            case 3: str=PetState.waitingAdopted.toString();break;
            case 4: str=PetState.findBack.toString();break;
            case 5: str=PetState.adopted.toString();break;
            case 6: str=PetState.dead.toString();break;
            default:return (List<Pet>) petRepository.findAll();
        }
        return petRepository.findAllByAdoptState(str);
    }

    public Boolean update(PetUpdateInfo updateInfo){
        Pet pet=petRepository.findById(updateInfo.petID).orElse(null);
        if(pet==null){
            return false;
        }
        if(!Objects.equals(updateInfo.adoptTime, null)){
            pet.setAdoptTime(TimeConvert.convertStringToLocalDate(updateInfo.adoptTime));
        }
        if(!Objects.equals(updateInfo.adoptUserID, null)){
            User user=userRepository.findById(updateInfo.adoptUserID).orElse(null);
            if(user==null)return false;
            pet.setAdoptUser(user);
        }
        if(!Objects.equals(updateInfo.color, null)){
            pet.setColor(updateInfo.color);
        }
        if(updateInfo.age!=null){
            pet.setAge(updateInfo.age);
        }
        if(!Objects.equals(updateInfo.area, null)){
            pet.setArea(updateInfo.area);
        }
        if(!Objects.equals(updateInfo.photo, null)){
            pet.setPhoto(updateInfo.photo);
        }
        if(!Objects.equals(updateInfo.description, null)){
            pet.setDescription(updateInfo.description);
        }
        if(!Objects.equals(updateInfo.type, null)){
            pet.setType(updateInfo.type);
        }
        if(!Objects.equals(updateInfo.name, null)){
            pet.setName(updateInfo.name);
        }
        if(!Objects.equals(updateInfo.price, null)){
            pet.setPrice(updateInfo.price);
        }
        petRepository.save(pet);

        return true;
    }

    //根据数量生成6位ID
    private String generateID(){
        long c= petRepository.count()+1;
        StringBuilder s= new StringBuilder();
        while(true){
            s=new StringBuilder(Long.toString(c));
            for(int i=0;i<6-s.length();i++){
                s.insert(0, "0");
            }
            s.insert(0, "P");
            if(!petRepository.existsById(s.toString())){
                break;
            }
            c++;
        }

        return s.toString();
    }
}
