package com.example.demo.service.base;
import com.example.demo.dto.DeviceStatus;
import org.springframework.stereotype.Repository;

import java.util.Map;

public interface RedisService {
    void saveDeviceStatus(DeviceStatus deviceStatus);
    DeviceStatus getDeviceStatus(String deviceName);
    void updateDeviceStatus(DeviceStatus emp);
    Map<String, DeviceStatus> getAllDeviceStatus();
    void deleteDeviceStatus(String key);

    void deleteAllDeviceStatus();
    void saveDeviceStatus(Map<String, DeviceStatus> map);
}
