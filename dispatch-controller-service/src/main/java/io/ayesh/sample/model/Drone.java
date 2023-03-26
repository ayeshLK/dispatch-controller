package io.ayesh.sample.model;

import io.ayesh.sample.validation.EnumNamePattern;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Drone {
    private int id;
    @NotBlank
    @Size(
            max = 100,
            message = "The maximum allowed length for the Serial Number is {max} characters"
    )
    private String serialNumber;
    @NotBlank
    @EnumNamePattern(
            regexp = "Lightweight|Middleweight|Cruiserweight|Heavyweight",
            message = "The drone model must one of Lightweight, Middleweight, Cruiserweight, or Heavyweight"
    )
    private DroneModel model;
    @NotNull
    @DecimalMax(
            value = "500.00",
            message = "The maximum allowed weight is {max} grams"
    )
    @Digits(integer=3, fraction=2)
    private double weightLimit;
    @NotNull
    @DecimalMax(
            value = "1.00",
            message = "The battery percentage must be less than or equal to {max}"
    )
    @Digits(integer=1, fraction=2)
    private double batteryCapacity;
    @EnumNamePattern(
            regexp = "IDLE",
            message = "The drone state must be IDLE"
    )
    private DroneStatus state = DroneStatus.IDLE;
}
