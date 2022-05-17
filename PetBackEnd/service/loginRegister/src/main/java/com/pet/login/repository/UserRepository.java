package com.pet.login.repository;

import com.pet.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User,String> {
    User getUsersByEmail(String email);

    List<User> getUsersByBlacklist(Integer isBlack);
}
