package io.ayesh.sample.repository;

import io.ayesh.sample.model.BatteryCapacity;
import io.ayesh.sample.model.Drone;
import io.ayesh.sample.model.DroneStatus;

import java.util.List;
import java.util.Optional;

public interface DroneRepository {
    boolean droneExists(String serialNumber);

    int addDrone(Drone drone);

    Optional<Drone> getDrone(int droneId);

    List<Drone> getDronesForStatus(List<DroneStatus> droneStatus);

    Optional<BatteryCapacity> getDroneBatterCapacity(int droneId);
}
