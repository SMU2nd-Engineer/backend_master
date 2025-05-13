package com.culturemoa.cultureMoaProject.common.counters.repository;

import com.culturemoa.cultureMoaProject.common.counters.dto.CountersDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CountersRepository extends MongoRepository<CountersDTO, String> {
}
