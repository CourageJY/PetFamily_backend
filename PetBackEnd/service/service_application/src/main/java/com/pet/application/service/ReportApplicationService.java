package com.pet.application.service;

import com.pet.application.entity.FindApplicationUpdate;
import com.pet.application.entity.ReportApplicationUpdate;
import com.pet.application.repository.ReportApplicationRepository;
import com.pet.login.service.EmailService;
import com.pet.models.FindApplication;
import com.pet.models.ReportApplication;
import com.pet.util.enums.ApplicationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReportApplicationService {
    @Autowired
    ReportApplicationRepository reportApplication;

    @Autowired
    EmailService emailService;

    public void create(ReportApplication report){
        reportApplication.save(report);
    }

    public boolean deleteByID(String reportApplicationID){
        try {
            reportApplication.deleteById(reportApplicationID);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public ReportApplication findByID(String reportApplicationID){
        Optional<ReportApplication> byId = reportApplication.findById(reportApplicationID);
        return byId.orElse(null);
    }

    public List<ReportApplication> findByState(String status){
        Optional<List<ReportApplication>> byId = Optional.ofNullable(reportApplication.findAllByStatus(status));
        return byId.orElse(null);
    }

    public boolean Update(ReportApplicationUpdate reportApplicationUpdate){
        ReportApplication application=reportApplication.findById(reportApplicationUpdate
                                                          .getApplicationID())
                                                          .orElse(null);

        //为空的不修改
        if(!Objects.equals(reportApplicationUpdate.getTitle(), null)){
            application.setTitle(reportApplicationUpdate.getTitle());
        }
        if(reportApplicationUpdate.getTime()!=null){
            application.setTime(reportApplicationUpdate.getTime());
        }
        if(!Objects.equals(reportApplicationUpdate.getDescription(), null)){
            application.setDescription(reportApplicationUpdate.getDescription());
        }
        if(!Objects.equals(reportApplicationUpdate.getPhotoOne(), null)){
            application.setPhotoOne(reportApplicationUpdate.getPhotoOne());
        }
        if(!Objects.equals(reportApplicationUpdate.getPhotoTwo(), null)){
            application.setPhotoTwo(reportApplicationUpdate.getPhotoTwo());
        }
        if(!Objects.equals(reportApplicationUpdate.getStatus(), null)){
            String content=null;
            if(Objects.equals(reportApplicationUpdate.getStatus(), ApplicationState.approve.toString())){
                content="恭喜您，你于"+application.getTime()+"提交的上报申请已被审核通过！";
                try {
                    emailService.sendEmail(application.getUser().getEmail(),"上报申请审核",content);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(Objects.equals(reportApplicationUpdate.getStatus(), ApplicationState.disapprove.toString())){
                content="抱歉，你于"+application.getTime()+"提交的上报申请未被审核通过！";
                try {
                    emailService.sendEmail(application.getUser().getEmail(),"上报申请审核",content);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            application.setStatus(reportApplicationUpdate.getStatus());
        }
        if(!Objects.equals(reportApplicationUpdate.getPhoneNum(), null)){
            application.setPhoneNum(reportApplicationUpdate.getPhoneNum());
        }
        if(reportApplicationUpdate.getLocationX()!=null){
            application.setLocationX(reportApplicationUpdate.getLocationX());
        }
        if(reportApplicationUpdate.getLocationY()!=null){
            application.setLocationY(reportApplicationUpdate.getLocationY());
        }

        //覆盖保存
        reportApplication.save(application);

        return true;
    }

    //根据数量生成6位ID
    public String generateID(){
        long c= reportApplication.count()+1;
        StringBuilder s= new StringBuilder();
        while(true){
            s=new StringBuilder(Long.toString(c));
            for(int i=0;i<6-s.length();i++){
                s.insert(0, "0");
            }
            s.insert(0, "R");
            if(!reportApplication.existsById(s.toString())){
                break;
            }
            c++;
        }

        return s.toString();
    }
}
