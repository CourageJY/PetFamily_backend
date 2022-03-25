package com.pet.application.service;

import com.pet.application.entity.ReportApplicationUpdate;
import com.pet.application.entity.SendApplicationUpdate;
import com.pet.application.repository.SendApplicationRepository;
import com.pet.login.service.EmailService;
import com.pet.models.ReportApplication;
import com.pet.models.SendApplication;
import com.pet.util.enums.ApplicationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SendApplicationService {
    @Autowired
    SendApplicationRepository sendApplication;

    @Autowired
    EmailService emailService;

    public void create(SendApplication send){
        sendApplication.save(send);
    }

    public boolean deleteByID(String sendApplicationID){
        try {
            sendApplication.deleteById(sendApplicationID);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public SendApplication findByID(String sendApplicationID){
        Optional<SendApplication> byId = sendApplication.findById(sendApplicationID);
        return byId.orElse(null);
    }

    public List<SendApplication> findByState(String status){
        Optional<List<SendApplication>> byId = Optional.ofNullable(sendApplication.findAllByStatus(status));
        return byId.orElse(null);
    }

    public boolean Update(SendApplicationUpdate sendApplicationUpdate){
        SendApplication application=sendApplication.findById(sendApplicationUpdate
                                                      .getApplicationID())
                                                      .orElse(null);
        //为空的不修改
        if(!Objects.equals(sendApplicationUpdate.getTitle(), null)){
            application.setTitle(sendApplicationUpdate.getTitle());
        }
        if(sendApplicationUpdate.getTime()!=null){
            application.setTime(sendApplicationUpdate.getTime());
        }
        if(!Objects.equals(sendApplicationUpdate.getReason(), null)){
            application.setReason(sendApplicationUpdate.getReason());
        }
        if(!Objects.equals(sendApplicationUpdate.getPetPhoto(), null)){
            application.setPetPhoto(sendApplicationUpdate.getPetPhoto());
        }
        if(!Objects.equals(sendApplicationUpdate.getCertificate(), null)){
            application.setCertificate(sendApplicationUpdate.getCertificate());
        }
        if(!Objects.equals(sendApplicationUpdate.getStatus(), null)){
            String content=null;
            if(Objects.equals(sendApplicationUpdate.getStatus(), ApplicationState.approve.toString())){
                content="恭喜您，你于"+application.getTime()+"提交的送养申请已被审核通过！";
                try {
                    emailService.sendEmail(application.getUser().getEmail(),"送养申请审核",content);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(Objects.equals(sendApplicationUpdate.getStatus(), ApplicationState.disapprove.toString())){
                content="抱歉，你于"+application.getTime()+"提交的送养申请未被审核通过！";
                try {
                    emailService.sendEmail(application.getUser().getEmail(),"送养申请审核",content);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            application.setStatus(sendApplicationUpdate.getStatus());
        }
        if(!Objects.equals(sendApplicationUpdate.getPhoneNum(), null)){
            application.setPhoneNum(sendApplicationUpdate.getPhoneNum());
        }
        if(!Objects.equals(sendApplicationUpdate.getLocation(), null)){
            application.setLocation(sendApplicationUpdate.getLocation());
        }
        if(sendApplicationUpdate.getSendTime()!=null){
            application.setSendTime(sendApplicationUpdate.getSendTime());
        }

        //覆盖保存
        sendApplication.save(application);

        return true;
    }

    //根据数量生成ID
    public String generateID(){
        long c= sendApplication.count()+1;
        StringBuilder s= new StringBuilder();
        while(true){
            s=new StringBuilder(Long.toString(c));
            for(int i=0;i<6-s.length();i++){
                s.insert(0, "0");
            }
            s.insert(0, "S");
            if(!sendApplication.existsById(s.toString())){
                break;
            }
            c++;
        }

        return s.toString();
    }
}
