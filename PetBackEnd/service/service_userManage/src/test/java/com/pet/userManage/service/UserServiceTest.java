package com.pet.userManage.service;

import com.pet.login.service.UserService;
import com.pet.models.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;
    @Test
    void getById() {
        User user=userService.getById("1950001");
        System.out.println(user);
    }

    @Test
    @Transactional
    void deleteById() {
        Boolean flag=userService.deleteById("1950001");
        System.out.println(flag);
    }
}