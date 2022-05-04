package com.example.demo.service.base;

import com.example.demo.dto.CacheDeviceStatusResponse;
import com.example.demo.dto.DeviceStatus;
import com.example.demo.entity.AuditLogging;

import java.util.Map;

public interface LoggingService {
    void log(AuditLogging auditLogging);
}
