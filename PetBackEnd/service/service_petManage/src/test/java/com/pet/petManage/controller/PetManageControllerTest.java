//package com.pet.petManage.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.pet.petManage.entity.AddPetRequest;
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
//
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
//@SpringBootTest
//class PetManageControllerTest {
//
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
//    @Transactional
//    void addPet() throws Exception {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyOTAwMDAwIiwicm9sZSI6Ikluc3RpdHV0aW9uIiwiZXhwIjoxNjQxMDY3OTIzLCJpYXQiOjE2NDA4NTE5MjN9.W_62tqkrgj1xdN-3_zsrtbBPIJwL-P3otdSkUWhHI6g");
//        AddPetRequest addPetRequest=new AddPetRequest("dogdog","猫",5,true,"黑色","测试小猫","","上海");
//        String uri="/api/pet/manage/add";
//        mockMvc.perform(post(uri).headers(headers).contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(addPetRequest)))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn().getResponse().getContentAsString();
//    }
//
//    @Test
//    @Transactional
//    void updatePetState()throws Exception {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyOTAwMDAwIiwicm9sZSI6Ikluc3RpdHV0aW9uIiwiZXhwIjoxNjQxMDY3OTIzLCJpYXQiOjE2NDA4NTE5MjN9.W_62tqkrgj1xdN-3_zsrtbBPIJwL-P3otdSkUWhHI6g");
//        String uri="/api/pet/manage/UpdatePetState";
//        mockMvc.perform(post(uri).headers(headers).param("petID","P0001").param("state", String.valueOf(5)))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn().getResponse().getContentAsString();
//    }
//}