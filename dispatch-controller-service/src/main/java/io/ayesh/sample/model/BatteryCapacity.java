package io.ayesh.sample.model;

import lombok.Data;

@Data
public class BatteryCapacity {
    private int droneId;
    private double batteryPercentage;
}
