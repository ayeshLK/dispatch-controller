package io.ayesh.sample.model;

import lombok.Data;

@Data
public class Medication {
    private int loadId;
    private String code;
    private String name;
    private float weight;
    private String image;
}
