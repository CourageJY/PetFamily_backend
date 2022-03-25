package com.pet.util.utils;

import com.pet.models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    @Test
    void getToken() {
        User user=new User();
        user.setPassword("123456");
        user.setSalt(Encryption.generateSalt());
        user.setName("jy");
        user.setEmail("1729145790@qq.com");
        user.setId("1952344");
        System.out.println(JwtUtil.getToken(user));
    }
}