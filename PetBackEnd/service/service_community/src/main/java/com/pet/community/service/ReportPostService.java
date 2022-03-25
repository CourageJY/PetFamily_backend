package com.pet.community.service;

import com.pet.community.repository.CommentRepository;
import com.pet.community.repository.ReportPostRepository;
import com.pet.models.Comment;
import com.pet.models.ReportPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportPostService {
    static private int n=8;//默认8位id

    @Autowired
    private ReportPostRepository reportPostRepository;

    public ReportPost getByID(String id){
        return reportPostRepository.findById(id).orElse(null);
    }

    public void createOrUpdate(ReportPost reportPost){
        reportPostRepository.save(reportPost);
    }

    public List<ReportPost> getAll(){
        return (List<ReportPost>) reportPostRepository.findAll();
    }

    public void deleteByID(String id){
        reportPostRepository.deleteById(id);
    }

    public List<ReportPost> getAllByPostId(String postId){
        return reportPostRepository.findAllByPostId(postId);
    }

    //根据数量生成n位id
    public String generateID(){
        long c= reportPostRepository.count()+1;
        StringBuilder s= new StringBuilder();
        while(true){
            s=new StringBuilder(Long.toString(c));
            for(int i=0;i<n-s.length();i++){
                s.insert(0, "0");
            }
            s.insert(0, "C");
            if(!reportPostRepository.existsById(s.toString())){
                break;
            }
            c++;
        }

        return s.toString();
    }

}
