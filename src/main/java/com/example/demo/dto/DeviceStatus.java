package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Builder
@Data
public class DeviceStatus implements Serializable {
    private String deviceName;
    private String deviceStatus;
    private Date savedDate;
}
