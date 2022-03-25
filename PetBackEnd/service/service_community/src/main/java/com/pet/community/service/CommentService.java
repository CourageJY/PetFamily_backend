package com.pet.community.service;

import com.pet.community.repository.CommentRepository;
import com.pet.models.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommentService {
    static private int n=8;//默认8位id

    @Autowired
    private CommentRepository commentRepository;

    public Comment getByID(String id){
        return commentRepository.findById(id).orElse(null);
    }

    public void createOrUpdate(Comment comment){
        commentRepository.save(comment);
    }

    public List<Comment> getAll(){
        return (List<Comment>) commentRepository.findAll();
    }

    public void deleteByID(String id){
        commentRepository.deleteById(id);
    }

    public List<Comment> getAllByPostId(String postId){
        return commentRepository.findAllByPostId(postId);
    }

    //根据数量生成n位id
    public String generateID(){
        long c= commentRepository.count()+1;
        StringBuilder s= new StringBuilder();
        while(true){
            s=new StringBuilder(Long.toString(c));
            for(int i=0;i<n-s.length();i++){
                s.insert(0, "0");
            }
            s.insert(0, "C");
            if(!commentRepository.existsById(s.toString())){
                break;
            }
            c++;
        }

        return s.toString();
    }
}


