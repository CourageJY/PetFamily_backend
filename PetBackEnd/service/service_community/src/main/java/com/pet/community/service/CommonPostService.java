package com.pet.community.service;

import com.pet.community.repository.CommonPostRepository;
import com.pet.community.repository.NoticeRepository;
import com.pet.models.CommonPost;
import com.pet.models.Notice;
import com.pet.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommonPostService {
    static private int n=8;//默认8位id

    @Autowired
    private CommonPostRepository commonPostRepository;

    public CommonPost getByID(String id){
        return commonPostRepository.findById(id).orElse(null);
    }

    public void createOrUpdate(CommonPost commonPost){
        commonPostRepository.save(commonPost);
    }

    public List<CommonPost> getAll(){
        return (List<CommonPost>) commonPostRepository.findAll();
    }

    public void deleteByID(String id){
        commonPostRepository.deleteById(id);
    }

    public List<CommonPost> findAllByUserId(User user){
        return commonPostRepository.findAllByUser(user);
    }

    public List<CommonPost> findAllByStatus(String status){
        return commonPostRepository.findAllByStatus(status);
    }

    //根据数量生成n位id
    public String generateID(){
        long c= commonPostRepository.count()+1;
        StringBuilder s= new StringBuilder();
        while(true){
            s=new StringBuilder(Long.toString(c));
            for(int i=0;i<n-s.length();i++){
                s.insert(0, "0");
            }
            s.insert(0, "CP");
            if(!commonPostRepository.existsById(s.toString())){
                break;
            }
            c++;
        }

        return s.toString();
    }
}


