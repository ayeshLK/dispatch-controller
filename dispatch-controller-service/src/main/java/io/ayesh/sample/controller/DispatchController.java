package io.ayesh.sample.controller;

import io.ayesh.sample.hateoas.DroneModel;
import io.ayesh.sample.hateoas.DroneModelAssembler;
import io.ayesh.sample.model.BatteryCapacity;
import io.ayesh.sample.model.Drone;
import io.ayesh.sample.model.Medication;
import io.ayesh.sample.responses.ServiceResponses;
import io.ayesh.sample.service.DispatchControllerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/drones")
@Validated
public class DispatchController {
    private final DispatchControllerService dispatchControllerService;
    private final DroneModelAssembler droneModelAssembler;

    @Autowired
    public DispatchController(DispatchControllerService dispatchControllerService,
                              DroneModelAssembler droneModelAssembler) {
        this.dispatchControllerService = dispatchControllerService;
        this.droneModelAssembler = droneModelAssembler;
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CollectionModel<DroneModel>> getDronesAvailableForLoading() {
        List<Drone> availableDrones = dispatchControllerService.getDronesAvailableForLoading();
        CollectionModel<DroneModel> droneModels = droneModelAssembler.toCollectionModel(availableDrones);
        return ResponseEntity.ok(droneModels);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<DroneModel> registerDrone(@Valid @RequestBody Drone drone) {
        Drone createdDrone = dispatchControllerService.registerDrone(drone);
        DroneModel droneModel = droneModelAssembler.toModel(createdDrone);
        return ResponseEntity.status(HttpStatus.CREATED).body(droneModel);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<DroneModel> getDrone(@PathVariable("id") int droneId) {
        Drone drone = dispatchControllerService.getDrone(droneId);
        DroneModel droneModel = droneModelAssembler.toModel(drone);
        return ResponseEntity.ok().body(droneModel);
    }

    @GetMapping(
            value = "/{id}/battery-capacity",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<BatteryCapacity> getBatteryCapacity(@PathVariable("id") int droneId) {
        BatteryCapacity batteryCapacity = dispatchControllerService.getBatteryCapacity(droneId);
        return ResponseEntity.ok().body(batteryCapacity);
    }

    @GetMapping(
            value = "/{id}/medications",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Medication>> getMedications(@PathVariable("id") int droneId) {
        List<Medication> loadedMedications = dispatchControllerService.getLoadedMedications(droneId);
        return ResponseEntity.ok().body(loadedMedications);
    }

    @PostMapping(
            value = "/{id}/medications",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ServiceResponses.CommonResponse> loadMedication(@PathVariable("id") int droneId,
                                                                   @RequestBody
                                                                   @NotEmpty(
                                                                           message = "Medication list cannot be empty"
                                                                   )
                                                                   List<@Valid Medication> medications) {
        dispatchControllerService.loadMedication(droneId, medications);
        return ResponseEntity.accepted().body(
                new ServiceResponses.CommonResponse("Medications were successfully loaded onto the drone"));
    }
}
