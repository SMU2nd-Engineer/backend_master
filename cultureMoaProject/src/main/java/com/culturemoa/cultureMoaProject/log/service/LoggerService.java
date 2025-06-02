package com.culturemoa.cultureMoaProject.log.service;

import com.culturemoa.cultureMoaProject.common.counters.service.CountersService;
import com.culturemoa.cultureMoaProject.log.dto.LoggerDTO;
import com.culturemoa.cultureMoaProject.log.repository.LoggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class LoggerService {
    private final LoggerRepository loggerRepository;
    private final CountersService countersService;
    private final String TYPE = "logger";

    @Autowired
    public LoggerService(LoggerRepository loggerRepository, CountersService countersService) {
        this.loggerRepository = loggerRepository;
        this.countersService = countersService;
    }

    public void insertLogger(LoggerDTO loggerDTO){
        loggerDTO.setId(countersService.getId(TYPE));
        loggerDTO.setTime(LocalDateTime.now());
        loggerRepository.insert(loggerDTO);
    }
}
