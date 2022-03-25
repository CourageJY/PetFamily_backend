package com.pet.application.repository;

import com.pet.models.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet,String> {

}
