package io.ayesh.sample.model;

import lombok.Data;

@Data
public class Drone {
    private int id;
    private String serialNumber;
    private DroneModel model;
    private double weightLimit;
    private double batteryCapacity;
    private DroneStatus state;
}
