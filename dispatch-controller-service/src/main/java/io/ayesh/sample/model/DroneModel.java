package io.ayesh.sample.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
public enum DroneModel {
    LIGHT_WEIGHT("Lightweight"),
    MIDDLE_WEIGHT("Middleweight"),
    CRUISER_WEIGHT("Cruiserweight"),
    HEAVY_WEIGHT("Heavyweight");

    private @Getter final String name;

    public Optional<DroneModel> getModel(String modelName) {
        return Arrays.stream(DroneModel.values())
                .filter(drone -> drone.name.equals(modelName))
                .findFirst();
    }
}
