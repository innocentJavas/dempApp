package com.example.demo.service;

import com.example.demo.dto.DeviceStatus;
import com.example.demo.service.base.RedisService;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;

@Repository
public class RedisServiceImpl implements RedisService {
    private final String hashReference= "DeviceStatus";

    @Resource(name="redisTemplate")          // 'redisTemplate' is defined as a Bean in AppConfig.java
    private HashOperations<String, String, DeviceStatus> hashOperations;

    @Override
    public void saveDeviceStatus(DeviceStatus deviceStatus) {
        //creates one record in Redis DB if record with that Id is not present
        hashOperations.putIfAbsent(hashReference, deviceStatus.getDeviceName(), deviceStatus);
    }

    @Override
    public void saveDeviceStatus(Map<String, DeviceStatus> map) {
        hashOperations.putAll(hashReference, map);
    }

    @Override
    public DeviceStatus getDeviceStatus(String key) {
        return hashOperations.get(hashReference, key);
    }

    @Override
    public void updateDeviceStatus(DeviceStatus deviceStatus) {
        hashOperations.put(hashReference, deviceStatus.getDeviceName(), deviceStatus);
    }

    @Override
    public Map<String, DeviceStatus> getAllDeviceStatus() {
        return hashOperations.entries(hashReference);
    }

    @Override
    public void deleteDeviceStatus(String key) {
        hashOperations.delete(hashReference, key);
    }

    @Override
    public void deleteAllDeviceStatus() {
        Map<String, DeviceStatus> deviceStatusMap = getAllDeviceStatus();
        for (String key: deviceStatusMap.keySet()) {
            deleteDeviceStatus(key);
        }
    }
}
