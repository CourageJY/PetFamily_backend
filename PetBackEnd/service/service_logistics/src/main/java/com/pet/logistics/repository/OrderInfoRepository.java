package com.pet.logistics.repository;

import com.pet.models.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderInfoRepository extends CrudRepository<Comment,String> {
}
