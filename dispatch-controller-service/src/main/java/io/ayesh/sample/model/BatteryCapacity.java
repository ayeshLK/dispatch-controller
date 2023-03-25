package io.ayesh.sample.model;

import lombok.Data;

@Data
public class BatteryCapacity {
    private String droneSerialNumber;
    private double batteryPercentage;
}
