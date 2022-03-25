package com.pet.application.service;

import com.pet.application.entity.AdoptApplicationUpdate;
import com.pet.application.repository.AdoptApplicationRepository;
import com.pet.login.service.EmailService;
import com.pet.models.AdoptApplication;
import com.pet.util.enums.ApplicationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AdoptApplicationService {
    @Autowired
    AdoptApplicationRepository adoptApplication;

    @Autowired
    EmailService emailService;

    public void create(AdoptApplication adopt){
        adoptApplication.save(adopt);
    }

    public boolean deleteByID(String applicationID){
        try {
            adoptApplication.deleteById(applicationID);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public AdoptApplication findByID(String applicationID){
        Optional<AdoptApplication> byId = adoptApplication.findById(applicationID);
        return byId.orElse(null);
    }

    public List<AdoptApplication> findByState(String status){
        Optional<List<AdoptApplication>> byId = Optional.ofNullable(adoptApplication.findAllByStatus(status));
        return byId.orElse(null);
    }

    public boolean Update(AdoptApplicationUpdate adoptApplicationUpdate){
        AdoptApplication application=adoptApplication.findById(adoptApplicationUpdate
                                                        .getApplicationID())
                                                        .orElse(null);
        //为空的不修改
        if(!Objects.equals(adoptApplicationUpdate.getTitle(), null)){
            application.setTitle(adoptApplicationUpdate.getTitle());
        }
        if(adoptApplicationUpdate.getTime()!=null){
            application.setTime(adoptApplicationUpdate.getTime());
        }
        if(!Objects.equals(adoptApplicationUpdate.getReason(), null)){
            application.setReason(adoptApplicationUpdate.getReason());
        }
        if(!Objects.equals(adoptApplicationUpdate.getEconomicCondition(), null)){
            application.setEconomicCondition(adoptApplicationUpdate.getEconomicCondition());
        }
        if(!Objects.equals(adoptApplicationUpdate.getStatus(), null)){
            String content=null;
            if(Objects.equals(adoptApplicationUpdate.getStatus(), ApplicationState.approve.toString())){
                content="恭喜您，你于"+application.getTime()+"提交的领养申请已被审核通过！";
                try {
                    emailService.sendEmail(application.getUser().getEmail(),"领养申请审核",content);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(Objects.equals(adoptApplicationUpdate.getStatus(), ApplicationState.disapprove.toString())){
                content="抱歉，你于"+application.getTime()+"提交的领养申请未被审核通过！";
                try {
                    emailService.sendEmail(application.getUser().getEmail(),"领养申请审核",content);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            application.setStatus(adoptApplicationUpdate.getStatus());
        }
        if(!Objects.equals(adoptApplicationUpdate.getPhoneNum(), null)){
            application.setPhoneNum(adoptApplicationUpdate.getPhoneNum());
        }
        if(!Objects.equals(adoptApplicationUpdate.getArea(), null)){
            application.setArea(adoptApplicationUpdate.getArea());
        }

        //覆盖保存
        adoptApplication.save(application);

        return true;
    }

    //根据数量生成6位ID
    public String generateID(){
        long c= adoptApplication.count()+1;
        StringBuilder s= new StringBuilder();
        while(true){
            s=new StringBuilder(Long.toString(c));
            for(int i=0;i<6-s.length();i++){
                s.insert(0, "0");
            }
            s.insert(0, "A");
            if(!adoptApplication.existsById(s.toString())){
                break;
            }
            c++;
        }

        return s.toString();
    }

}
