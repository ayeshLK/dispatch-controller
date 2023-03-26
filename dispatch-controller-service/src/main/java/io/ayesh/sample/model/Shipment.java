package io.ayesh.sample.model;

import lombok.Data;

@Data
public class Shipment {
    private int id;
    private int droneId;
    private ShipmentStatus status = ShipmentStatus.IN_PROGRESS;
}
