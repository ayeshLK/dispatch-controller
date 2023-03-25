package io.ayesh.sample.model;

import lombok.Data;

@Data
public class Drone {
    private int id;
    private String serialNumber;
    private DroneModel model;
    private float weightLimit;
    private float batteryPercentage;
    private DroneStatus state;
}
