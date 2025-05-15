package com.culturemoa.cultureMoaProject.log.repository;

import com.culturemoa.cultureMoaProject.log.dto.LoggerDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoggerRepository extends MongoRepository<LoggerDTO,String> {
}
