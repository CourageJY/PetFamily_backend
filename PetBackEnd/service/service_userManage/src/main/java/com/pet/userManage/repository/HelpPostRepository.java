package com.pet.userManage.repository;

import com.pet.models.HelpPost;
import com.pet.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HelpPostRepository extends CrudRepository<HelpPost,String> {
    List<HelpPost> getAllByUser(User user);
}
