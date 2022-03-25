package com.pet.community.repository;

import com.pet.models.Comment;
import com.pet.models.ReportPost;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReportPostRepository extends CrudRepository<ReportPost,String> {
    List<ReportPost> findAllByPostId(String postId);
}
