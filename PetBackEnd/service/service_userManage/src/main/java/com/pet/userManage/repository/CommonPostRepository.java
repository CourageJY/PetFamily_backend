package com.pet.userManage.repository;

import com.pet.models.CommonPost;
import com.pet.models.HelpPost;
import com.pet.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommonPostRepository extends CrudRepository<CommonPost,String> {
    List<CommonPost> getAllByUser(User user);
}
