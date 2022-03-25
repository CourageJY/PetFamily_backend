package com.pet.community.repository;

import com.pet.models.Notice;
import org.springframework.data.repository.CrudRepository;

public interface NoticeRepository extends CrudRepository<Notice,String> {
}
