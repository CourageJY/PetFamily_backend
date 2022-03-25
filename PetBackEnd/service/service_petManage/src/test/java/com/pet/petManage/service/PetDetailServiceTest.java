package com.pet.petManage.service;

import com.pet.models.Pet;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class PetDetailServiceTest {
    @Autowired
    PetDetailService petDetailService;

    @Test
    void getById() {
        Pet pet=petDetailService.getById("P0001");
        System.out.println(pet);
    }
}