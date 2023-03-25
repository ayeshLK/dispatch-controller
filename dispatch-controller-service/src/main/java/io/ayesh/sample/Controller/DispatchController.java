package io.ayesh.sample.Controller;

import io.ayesh.sample.model.BatteryCapacity;
import io.ayesh.sample.model.Drone;
import io.ayesh.sample.model.Medication;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class DispatchController {
    @PostMapping(
            value = "/drones",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Drone> registerDrone(@Valid @RequestBody Drone drone) {
        return ResponseEntity.status(HttpStatus.CREATED).body(drone);
    }

    @GetMapping(
            value = "/drones",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<List<Drone>> getDronesAvailableForLoading() {
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping(
            value = "/drones/{serialNumber}/battery-capacity",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<BatteryCapacity> getBatteryCapacity(@PathVariable("serialNumber") String serialNumber) {
        BatteryCapacity batteryCapacity = new BatteryCapacity();
        batteryCapacity.setDroneSerialNumber("abc1");
        batteryCapacity.setBatteryPercentage(0.89);
        return ResponseEntity.ok().body(batteryCapacity);
    }

    @GetMapping(
            value = "/drones/{serialNumber}/medications",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<List<Medication>> getMedications(@PathVariable("serialNumber") String serialNumber) {
        return ResponseEntity.ok().body(Collections.emptyList());
    }

    @PostMapping(
            value = "/drones/{serialNumber}/medications",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<String> loadMedication(@PathVariable("serialNumber") String serialNumber,
                                          @Valid @RequestBody List<Medication> medications) {
        return ResponseEntity.accepted().build();
    }
}
