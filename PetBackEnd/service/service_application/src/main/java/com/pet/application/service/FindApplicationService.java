package com.pet.application.service;

import com.pet.application.entity.AdoptApplicationUpdate;
import com.pet.application.entity.FindApplicationUpdate;
import com.pet.application.repository.FindApplicationRepository;
import com.pet.login.service.EmailService;
import com.pet.models.AdoptApplication;
import com.pet.models.FindApplication;
import com.pet.util.enums.ApplicationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FindApplicationService {
    @Autowired
    FindApplicationRepository findApplication;

    @Autowired
    EmailService emailService;

    public void create(FindApplication find){
        findApplication.save(find);
    }

    public boolean deleteByID(String findApplicationID){
        try {
            findApplication.deleteById(findApplicationID);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public FindApplication findByID(String findApplicationID){
        Optional<FindApplication> byId = findApplication.findById(findApplicationID);
        return byId.orElse(null);
    }

    public List<FindApplication> findByState(String status){
        Optional<List<FindApplication>> byId = Optional.ofNullable(findApplication.findAllByStatus(status));
        return byId.orElse(null);
    }

    public boolean Update(FindApplicationUpdate findApplicationUpdate){
        FindApplication application=findApplication.findById(findApplicationUpdate
                                                      .getApplicationID())
                                                      .orElse(null);
        //为空的不修改
        if(!Objects.equals(findApplicationUpdate.getTitle(), null)){
            application.setTitle(findApplicationUpdate.getTitle());
        }
        if(findApplicationUpdate.getTime()!=null){
            application.setTime(findApplicationUpdate.getTime());
        }
        if(!Objects.equals(findApplicationUpdate.getDescription(), null)){
            application.setDescription(findApplicationUpdate.getDescription());
        }
        if(!Objects.equals(findApplicationUpdate.getPetPhoto(), null)){
            application.setPetPhoto(findApplicationUpdate.getPetPhoto());
        }
        if(!Objects.equals(findApplicationUpdate.getStatus(), null)){
            String content=null;
            if(Objects.equals(findApplicationUpdate.getStatus(), ApplicationState.approve.toString())){
                content="恭喜您，你于"+application.getTime()+"提交的寻回申请已被审核通过！";
                try {
                    emailService.sendEmail(application.getUser().getEmail(),"寻回申请审核",content);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(Objects.equals(findApplicationUpdate.getStatus(), ApplicationState.disapprove.toString())){
                content="抱歉，你于"+application.getTime()+"提交的寻回申请未被审核通过！";
                try {
                    emailService.sendEmail(application.getUser().getEmail(),"寻回申请审核",content);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            application.setStatus(findApplicationUpdate.getStatus());
        }
        if(!Objects.equals(findApplicationUpdate.getPhoneNum(), null)){
            application.setPhoneNum(findApplicationUpdate.getPhoneNum());
        }
        if(!Objects.equals(findApplicationUpdate.getCertificate(), null)){
            application.setCertificate(findApplicationUpdate.getCertificate());
        }

        //覆盖保存
        findApplication.save(application);

        return true;
    }

    //根据数量生成6位ID
    public String generateID(){
        long c= findApplication.count()+1;
        StringBuilder s= new StringBuilder();
        while(true){
            s=new StringBuilder(Long.toString(c));
            for(int i=0;i<6-s.length();i++){
                s.insert(0, "0");
            }
            s.insert(0, "F");
            if(!findApplication.existsById(s.toString())){
                break;
            }
            c++;
        }

        return s.toString();
    }
}
