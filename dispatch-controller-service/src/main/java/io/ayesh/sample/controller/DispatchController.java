package io.ayesh.sample.controller;

import io.ayesh.sample.model.BatteryCapacity;
import io.ayesh.sample.model.Drone;
import io.ayesh.sample.model.Medication;
import io.ayesh.sample.responses.ServiceResponses;
import io.ayesh.sample.service.DispatchControllerService;
import io.ayesh.sample.validation.DroneIdConstraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
public class DispatchController {
    private final DispatchControllerService dispatchControllerService;

    @Autowired
    public DispatchController(DispatchControllerService dispatchControllerService) {
        this.dispatchControllerService = dispatchControllerService;
    }

    @PostMapping(
            value = "/drones",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Drone> registerDrone(@Valid @RequestBody Drone drone) {
        Drone createdDrone = dispatchControllerService.registerDrone(drone);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDrone);
    }

    @GetMapping(
            value = "/drones",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<List<Drone>> getDronesAvailableForLoading() {
        List<Drone> availableDrones = dispatchControllerService.getDronesAvailableForLoading();
        return ResponseEntity.ok(availableDrones);
    }

    @GetMapping(
            value = "/drones/{id}/battery-capacity",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<BatteryCapacity> getBatteryCapacity(@PathVariable("id") @DroneIdConstraint int droneId) {
        BatteryCapacity batteryCapacity = dispatchControllerService.getBatteryCapacity(droneId);
        return ResponseEntity.ok().body(batteryCapacity);
    }

    @GetMapping(
            value = "/drones/{id}/medications",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<List<Medication>> getMedications(@PathVariable("id") @DroneIdConstraint int droneId) {
        List<Medication> loadedMedications = dispatchControllerService.getLoadedMedications(droneId);
        return ResponseEntity.ok().body(loadedMedications);
    }

    @PostMapping(
            value = "/drones/{id}/medications",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ServiceResponses.CommonResponse> loadMedication(@PathVariable("id") @DroneIdConstraint int droneId,
                                                                   @RequestBody
                                                                   @NotEmpty(
                                                                           message = "Medication list cannot be empty"
                                                                   )
                                                                   List<@Valid Medication> medications)
            throws Exception {
        dispatchControllerService.loadMedication(droneId, medications);
        return ResponseEntity.accepted().body(
                new ServiceResponses.CommonResponse("Medications were successfully loaded onto the drone"));
    }
}
