//package com.pet.userManage.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.pet.userManage.entity.UserInfo;
//import org.junit.Before;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import javax.transaction.Transactional;
////import javax.ws.rs.core.MediaType;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
//@SpringBootTest
//class UserInfoControllerTest {
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Before
//    public void setUp() throws Exception {
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }
//    @Test
//    void userDetail() throws Exception {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxOTUyMzQ0Iiwicm9sZSI6Ik5vcm1hbFVzZXIiLCJleHAiOjE2NDEwNjc1MzcsImlhdCI6MTY0MDg1MTUzN30.NWfMrq-6-c4Mlf5MnrGE8fT1ZWfYLO3mumr9WPngbH4");
//        String uri="/api/user/get";
//        mockMvc.perform(get(uri).headers(headers))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn().getResponse().getContentAsString();
//    }
//
//    @Transactional
//    @Test
//    void modifyUser() throws Exception {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxOTUyMzQ0Iiwicm9sZSI6Ik5vcm1hbFVzZXIiLCJleHAiOjE2NDEwNjc1MzcsImlhdCI6MTY0MDg1MTUzN30.NWfMrq-6-c4Mlf5MnrGE8fT1ZWfYLO3mumr9WPngbH4");
//        String uri="/api/user/modify";
//        UserInfo userInfo=new UserInfo("Tony","男","10000000000","1000000000@qq.com"," "," ","100");
//        mockMvc.perform(post(uri).headers(headers).contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(userInfo)))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn().getResponse().getContentAsString();
//    }
//}