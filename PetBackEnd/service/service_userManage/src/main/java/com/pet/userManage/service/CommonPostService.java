package com.pet.userManage.service;

import com.pet.models.CommonPost;
import com.pet.models.User;
import com.pet.userManage.repository.CommonPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommonPostService {
    @Autowired
    CommonPostRepository commonPostRepository;

    public List<CommonPost>getByUser(User user){
        Optional<List<CommonPost>> posts = Optional.of((List<CommonPost>)commonPostRepository.getAllByUser(user));
        return posts.orElse(null);
    }
}
