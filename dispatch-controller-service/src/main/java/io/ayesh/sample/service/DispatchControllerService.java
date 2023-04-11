package io.ayesh.sample.service;

import io.ayesh.sample.model.BatteryCapacity;
import io.ayesh.sample.model.Drone;
import io.ayesh.sample.model.Medication;

import java.util.List;

public interface DispatchControllerService {
    Drone registerDrone(Drone drone);

    List<Drone> getDronesAvailableForLoading();

    BatteryCapacity getBatteryCapacity(int droneId);

    List<Medication> getLoadedMedications(int droneId);

    void loadMedication(int droneId, List<Medication> medications);
}
