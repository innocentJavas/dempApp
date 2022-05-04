package com.example.demo.controller;

import com.example.demo.dto.CacheDeviceStatusResponse;
import com.example.demo.dto.DeviceStatus;
import com.example.demo.service.base.DeviceStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("devices/")
@Slf4j
public class DemoController {

    private final DeviceStatusService deviceStatusService;

    public DemoController(DeviceStatusService deviceStatusService) {
        this.deviceStatusService = deviceStatusService;
    }

    @PostMapping("status/v1")
    public ResponseEntity<CacheDeviceStatusResponse> getDeviceStatus(DeviceStatus deviceStatus){
        log.info(deviceStatus.getDeviceName());
        return ResponseEntity.status(HttpStatus.OK).body(deviceStatusService.cacheDeviceStatus(deviceStatus));
    }

    @GetMapping("devices/v1")
    public ResponseEntity<Map<String, DeviceStatus>> getAllDevices(){
        log.info("get all devices");
        return ResponseEntity.status(HttpStatus.OK).body(deviceStatusService.getAllDeviceStatuses());
    }


}
