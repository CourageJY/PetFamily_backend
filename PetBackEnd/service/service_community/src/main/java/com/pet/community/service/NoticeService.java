package com.pet.community.service;

import com.pet.community.repository.NoticeRepository;
import com.pet.models.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NoticeService {
    static private int n=8;//默认8位id

    @Autowired
    private NoticeRepository noticeRepository;

    public Notice getByID(String id){
        return noticeRepository.findById(id).orElse(null);
    }

    public void createOrUpdate(Notice notice){
        noticeRepository.save(notice);
    }

    public List<Notice> getAll(){
        return (List<Notice>) noticeRepository.findAll();
    }

    public void deleteByID(String id){
        noticeRepository.deleteById(id);
    }

    //根据数量生成n位id
    public String generateID(){
        long c= noticeRepository.count()+1;
        StringBuilder s= new StringBuilder();
        while(true){
            s=new StringBuilder(Long.toString(c));
            for(int i=0;i<n-s.length();i++){
                s.insert(0, "0");
            }
            s.insert(0, "N");
            if(!noticeRepository.existsById(s.toString())){
                break;
            }
            c++;
        }

        return s.toString();
    }
}


