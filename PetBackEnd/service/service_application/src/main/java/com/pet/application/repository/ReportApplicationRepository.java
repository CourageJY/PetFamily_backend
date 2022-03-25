package com.pet.application.repository;

import com.pet.models.FindApplication;
import com.pet.models.ReportApplication;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReportApplicationRepository extends CrudRepository<ReportApplication,String> {
    List<ReportApplication> findAllByStatus(String status);

}
