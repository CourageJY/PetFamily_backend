//package com.pet.application.controller;
//
//import org.junit.Before;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import javax.transaction.Transactional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
//@SpringBootTest
//class DeleteApplicationControllerTest {
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Before
//    public void setUp() throws Exception {
////        mockMvc = MockMvcBuilders.standaloneSetup(new LoginController()).build();//指定获取
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }
//
//    @Transactional
//    @Test
//    void deleteSendApplication() {
//        try {
//            mockMvc.perform(delete("/api/application/delete/sendApplication")
//                            .param("application_id","S0001"))
//                    .andExpect(status().isOk())
//                    .andDo(print())
//                    .andReturn().getResponse().getContentAsString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}