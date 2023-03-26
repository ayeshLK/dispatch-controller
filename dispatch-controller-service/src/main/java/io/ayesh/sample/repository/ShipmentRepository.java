package io.ayesh.sample.repository;

import io.ayesh.sample.model.Shipment;

import java.util.Optional;

public interface ShipmentRepository {
    int createNewShipment(int droneId);

    Optional<Shipment> getCurrentShipment(int droneId);
}
