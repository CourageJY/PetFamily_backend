package com.pet.petManage.service;

import com.pet.models.Pet;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class PetManageServiceTest {
    @Autowired
    PetManageService petManageService;
    @Test
    void getByStateTest() {
        List<Pet> pets = petManageService.getByState(1);
        System.out.println(pets);
    }
    @Test
    @Transactional
    void deleteByIdTest(){
        petManageService.deleteById("P0005");
    }
}