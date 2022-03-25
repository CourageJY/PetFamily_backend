package com.pet.community.service;

import com.pet.community.repository.HelpPostRepository;
import com.pet.models.CommonPost;
import com.pet.models.HelpPost;
import com.pet.models.ReportPost;
import com.pet.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HelpPostService {
    static private int n=8;//默认8位id

    @Autowired
    private HelpPostRepository helpPostRepository;

    public HelpPost getByID(String id){
        return helpPostRepository.findById(id).orElse(null);
    }

    public void createOrUpdate(HelpPost helpPost){
        helpPostRepository.save(helpPost);
    }

    public List<HelpPost> getAll(){
        return (List<HelpPost>) helpPostRepository.findAll();
    }

    public void deleteByID(String id){
        helpPostRepository.deleteById(id);
    }

    public List<HelpPost> findAllByUserId(User user){
        return helpPostRepository.findAllByUser(user);
    }

    public List<HelpPost> getAllByStatus(String status){
        return helpPostRepository.findAllByStatus(status);
    }
    //根据数量生成n位id
    public String generateID(){
        long c= helpPostRepository.count()+1;
        StringBuilder s= new StringBuilder();
        while(true){
            s=new StringBuilder(Long.toString(c));
            for(int i=0;i<n-s.length();i++){
                s.insert(0, "0");
            }
            s.insert(0, "HP");
            if(!helpPostRepository.existsById(s.toString())){
                break;
            }
            c++;
        }

        return s.toString();
    }
}


