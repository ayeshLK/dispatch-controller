package io.ayesh.sample.hateoas;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class DroneModel extends RepresentationModel<DroneModel> {
    private int id;
    private String serialNumber;
    private String model;
    private double weightLimit;
    private double batteryCapacity;
    private String state;
}
