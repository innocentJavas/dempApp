package com.example.demo.service;

import com.example.demo.dto.CacheDeviceStatusResponse;
import com.example.demo.dto.DeviceStatus;
import com.example.demo.entity.AuditLogging;
import com.example.demo.service.base.DeviceStatusService;
import com.example.demo.service.base.LoggingService;
import com.example.demo.service.base.RedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class DeviceStatusServiceImpl implements DeviceStatusService {

    private RedisService redisService;
    private LoggingService loggingService;

    public DeviceStatusServiceImpl(RedisService redisService, LoggingService loggingService) {
        this.redisService = redisService;
        this.loggingService = loggingService;
    }

    @Override
    public CacheDeviceStatusResponse cacheDeviceStatus(DeviceStatus deviceStatus) {
        //TODO - cache to redis
        try {
            log.info("saving to cache");
            deviceStatus.setSavedDate(new Date());
            redisService.saveDeviceStatus(deviceStatus);
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            logData(mapper.writeValueAsString(deviceStatus), "cacheDeviceStatus");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return CacheDeviceStatusResponse.builder()
                    .success(false)
                    .build();
        }
        return CacheDeviceStatusResponse.builder()
                .success(true)
                .build();
    }

    @Async("threadPoolTaskExecutor")
    public void logData(String requestBody, String callingMethod) {
        try {
            log.info("thread sleeping for 5 seconds");
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            log.info("failed to sleep");
        }

        log.info("logging data into database");
        AuditLogging auditLogging = AuditLogging.builder()
                .creationDate(new Date())
                .request(requestBody)
                .callingMethod(callingMethod)
                .build();

        loggingService.log(auditLogging);
    }

    @Override
    public Map<String, DeviceStatus> getAllDeviceStatuses() {
        logData(null, "getAllDeviceStatuses");
        return redisService.getAllDeviceStatus();
    }

    @Scheduled(fixedRate = 20000)
    private void deleteOldDevices() {
        log.info("checking to delete old statuses");
        Date currentDateTime = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDateTime);
        c.add(Calendar.MINUTE, -5);
        currentDateTime = c.getTime();
        Map<String, DeviceStatus> deviceStatuses = redisService.getAllDeviceStatus();
        for (String key : deviceStatuses.keySet()) {
            DeviceStatus deviceStatus = deviceStatuses.get(key);
            if (deviceStatus.getSavedDate().before(currentDateTime)) {
                log.info("deleting device name: " + key);
                redisService.deleteDeviceStatus(key);
            }
        }
    }
}
