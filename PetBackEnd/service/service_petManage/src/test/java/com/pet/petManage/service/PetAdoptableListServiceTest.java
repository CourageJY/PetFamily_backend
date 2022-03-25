package com.pet.petManage.service;

import com.pet.models.Pet;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class PetAdoptableListServiceTest {
    @Autowired
    PetAdoptableListService petAdoptableListService;

    @Test
    void findByAdoptState() {
        List<Pet> pets = petAdoptableListService.findByAdoptState();
        System.out.println(pets);
    }

}