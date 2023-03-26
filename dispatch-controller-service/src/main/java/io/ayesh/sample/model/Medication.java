package io.ayesh.sample.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Medication {
    private int id;
    private int shipmentId;
    @NotBlank
    @Pattern(
            regexp = "^[a-zA-Z0-9_-]*$",
            message = "Only alphanumeric characters, hyphens, or underscores are allowed for medication name"
    )
    private String name;
    @NotNull
    private double weight;
    @NotBlank
    @Pattern(
            regexp = "^[A-Z0-9_]*$",
            message = "Only uppercase letters, numbers, or underscores are allowed for medication code"
    )
    private String code;
    @NotBlank
    @Pattern(
            regexp = "^https?://.+$",
            message = "Valid URL is required for medication image"
    )
    private String image;
}
