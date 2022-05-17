package com.pet.userManage.service;

import com.pet.userManage.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;

    public Integer getPetCount(){
        return Math.toIntExact(petRepository.count());
    }
}
