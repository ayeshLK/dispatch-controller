package io.ayesh.sample.Controller;

import io.ayesh.sample.model.BatteryCapacity;
import io.ayesh.sample.model.Drone;
import io.ayesh.sample.model.Medication;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController("/drones")
public class DispatchController {
    @PostMapping
    ResponseEntity<Drone> registerDrone(@Valid @RequestBody Drone drone) {
        return ResponseEntity.status(HttpStatus.CREATED).body(drone);
    }

    @GetMapping
    ResponseEntity<List<Drone>> getDronesAvailableForLoading() {
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/${droneId}/battery-capacity")
    ResponseEntity<BatteryCapacity> getBatteryCapacity() {
        BatteryCapacity batteryCapacity = new BatteryCapacity();
        batteryCapacity.setDroneSerialNumber("abc1");
        batteryCapacity.setBatteryPercentage(0.89);
        return ResponseEntity.ok().body(batteryCapacity);
    }

    @GetMapping("/${droneId}/medications")
    ResponseEntity<List<Medication>> getMedications() {
        return ResponseEntity.ok().body(Collections.emptyList());
    }

    @PostMapping("/${droneId}/medications")
    ResponseEntity<String> loadMedication(@Valid @RequestBody List<Medication> medications) {
        return ResponseEntity.accepted().build();
    }
}
