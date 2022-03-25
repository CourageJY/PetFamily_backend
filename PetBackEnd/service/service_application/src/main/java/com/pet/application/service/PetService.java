package com.pet.application.service;

import com.pet.application.repository.PetRepository;
import com.pet.models.Pet;
import com.pet.util.enums.PetState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PetService {
    @Autowired
    PetRepository pet;

    public Pet findPetByID(String petID){
        Optional<Pet> p=pet.findById(petID);
        return p.orElse(null);
    }

    public boolean updateState(String petID,PetState state){
        Pet p=pet.findById(petID).orElse(null);
        if(pet==null){
            return false;
        }
        p.setAdoptState(state.toString());
        pet.save(p);
        return true;
    }
}
