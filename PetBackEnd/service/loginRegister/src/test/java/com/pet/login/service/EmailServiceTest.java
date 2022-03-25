package com.pet.login.service;

import com.pet.login.entity.Code;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EmailService.class)
class EmailServiceTest {
    @Autowired
    EmailService emailService;

    @Test
    void sendMimeMailTest() {
        String code="123456";
        Map<String, Code> map=new HashMap<>();
        String email="1729145790@qq.com";

        assertEquals(true, emailService.sendMimeMail(email,map,code));
    }
}