package com.example.demo.service;

import com.example.demo.LoggingRepository;
import com.example.demo.entity.AuditLogging;
import com.example.demo.service.base.LoggingService;
import com.example.demo.service.base.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoggingServiceImpl implements LoggingService {

    private LoggingRepository loggingRepository;

    public LoggingServiceImpl(LoggingRepository loggingRepository) {
        this.loggingRepository = loggingRepository;
    }

    @Override
    public void log(AuditLogging auditLogging) {
        loggingRepository.save(auditLogging);
//        loggingRepository.flush();
    }
}
