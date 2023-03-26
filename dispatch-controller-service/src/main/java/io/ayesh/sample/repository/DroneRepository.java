package io.ayesh.sample.repository;

import io.ayesh.sample.model.BatteryCapacity;
import io.ayesh.sample.model.Drone;
import io.ayesh.sample.model.DroneStatus;

import java.util.List;

public interface DroneRepository {
    int createDrone(Drone drone);

    boolean droneExistsById(int droneId);

    boolean droneExistsBySerialNumber(String serialNumber);

    Drone findDroneById(int droneId);

    List<Drone> findDronesByBatteryLevelLessThan(double batteryLevel);

    List<Drone> getDronesForStatus(List<DroneStatus> droneStatus);

    BatteryCapacity getDroneBatterCapacity(int droneId);
}
