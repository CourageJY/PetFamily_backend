package com.pet.login.service;

import com.pet.login.repository.UserRepository;
import com.pet.models.FindApplication;
import com.pet.models.Pet;
import com.pet.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository user;

    public User getById(String userID) {
        Optional<User> u=user.findById(userID);
        return u.orElse(null);
    }

    public User getByEmail(String email){
        return user.getUsersByEmail(email);
    }

    public void createOrUpdate(User u){
        user.save(u);
    }

    public Boolean deleteById(String userID ){
        try {
            user.deleteById(userID);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void modifyById(User u){
        user.save(u);
    }

    public List<User> getAllUser(){
        Optional<List<User>> users = Optional.of((List<User>)user.findAll());
        return users.orElse(null);
    }

}
