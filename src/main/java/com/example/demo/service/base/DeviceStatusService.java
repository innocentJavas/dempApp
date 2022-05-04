package com.example.demo.service.base;

import com.example.demo.dto.CacheDeviceStatusResponse;
import com.example.demo.dto.DeviceStatus;

import java.util.Map;

public interface DeviceStatusService {
    CacheDeviceStatusResponse cacheDeviceStatus(DeviceStatus deviceStatus);

    Map<String, DeviceStatus> getAllDeviceStatuses();
}
