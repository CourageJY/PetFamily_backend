package com.pet.community.repository;

import com.pet.models.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment,String> {
    List<Comment> findAllByPostId(String postId);
    List<Comment> findAllByStatus(String status);
}
