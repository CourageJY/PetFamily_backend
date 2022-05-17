package com.pet.userManage.service;

import com.pet.models.HelpPost;
import com.pet.models.User;
import com.pet.userManage.repository.HelpPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HelpPostService {
    @Autowired
    private HelpPostRepository helpPostRepository;

    public List<HelpPost> getByUser(User user){
        Optional<List<HelpPost>> posts = Optional.of((List<HelpPost>)helpPostRepository.getAllByUser(user));
        return posts.orElse(null);
    }
}
