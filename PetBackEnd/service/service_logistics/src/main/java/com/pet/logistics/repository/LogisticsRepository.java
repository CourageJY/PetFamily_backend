package com.pet.logistics.repository;

import com.pet.models.LogisticsInfo;
import com.pet.models.OrderInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LogisticsRepository extends CrudRepository<LogisticsInfo,String> {

}
