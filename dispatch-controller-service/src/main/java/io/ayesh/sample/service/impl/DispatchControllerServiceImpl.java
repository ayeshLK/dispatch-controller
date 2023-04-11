package io.ayesh.sample.service.impl;

import io.ayesh.sample.exceptions.DroneOverloadedException;
import io.ayesh.sample.exceptions.UnsupportedDroneStateException;
import io.ayesh.sample.model.BatteryCapacity;
import io.ayesh.sample.model.Drone;
import io.ayesh.sample.model.DroneStatus;
import io.ayesh.sample.model.Medication;
import io.ayesh.sample.model.Shipment;
import io.ayesh.sample.repository.DroneRepository;
import io.ayesh.sample.repository.MedicationRepository;
import io.ayesh.sample.repository.ShipmentRepository;
import io.ayesh.sample.service.DispatchControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DispatchControllerServiceImpl implements DispatchControllerService {
    private static final List<DroneStatus> VALID_DRONE_STATUS_FOR_LOADING = Arrays.asList(
            DroneStatus.IDLE, DroneStatus.LOADING);

    private final DroneRepository droneRepository;
    private final ShipmentRepository shipmentRepository;
    private final MedicationRepository medicationRepository;

    @Autowired
    public DispatchControllerServiceImpl(DroneRepository droneRepository,
                                         ShipmentRepository shipmentRepository,
                                         MedicationRepository medicationRepository) {
        this.droneRepository = droneRepository;
        this.shipmentRepository = shipmentRepository;
        this.medicationRepository = medicationRepository;
    }


    @Override
    public Drone registerDrone(Drone drone) {
        int droneId = droneRepository.createDrone(drone);
        drone.setId(droneId);
        return drone;
    }

    @Override
    public List<Drone> getDronesAvailableForLoading() {
        return droneRepository.getDronesForStatus(VALID_DRONE_STATUS_FOR_LOADING);
    }

    @Override
    public BatteryCapacity getBatteryCapacity(int droneId) {
        return droneRepository.getDroneBatterCapacity(droneId);
    }

    @Override
    public List<Medication> getLoadedMedications(int droneId) {
        Optional<Shipment> currentShipment = shipmentRepository.getCurrentShipment(droneId);
        if (currentShipment.isEmpty()) {
            return Collections.emptyList();
        }
        int shipmentId = currentShipment.get().getId();
        return medicationRepository.getLoadedMedicationItems(shipmentId);
    }

    @Override
    public void loadMedication(int droneId, List<Medication> medications) {
        Drone drone = droneRepository.findDroneById(droneId);
        if (!VALID_DRONE_STATUS_FOR_LOADING.contains(drone.getState())) {
            throw new UnsupportedDroneStateException("Current drone state does not support adding new medications");
        }
        Shipment currentShipment = getOrCreateLatestShipment(droneId);
        Double currentShipmentWeight = medicationRepository
                .getLoadedMedicationItems(currentShipment.getId())
                .stream()
                .map(Medication::getWeight)
                .reduce(0d, Double::sum);
        double remainingWeightForShipment = drone.getWeightLimit() - currentShipmentWeight;
        Double weightOfNewMedications = medications.stream().map(Medication::getWeight).reduce(0d, Double::sum);
        if (weightOfNewMedications > remainingWeightForShipment) {
            throw new DroneOverloadedException("Maximum capacity of the drone has reached");
        }
        medications.forEach(medication -> medication.setShipmentId(currentShipment.getId()));
        medicationRepository.createMedications(medications);
    }

    private Shipment getOrCreateLatestShipment(int droneId) {
        return shipmentRepository
                .getCurrentShipment(droneId)
                .orElseGet(() -> {
                    int shipmentId = shipmentRepository.createNewShipment(droneId);
                    Shipment shipment = new Shipment();
                    shipment.setId(shipmentId);
                    shipment.setDroneId(droneId);
                    return shipment;
                });
    }
}
