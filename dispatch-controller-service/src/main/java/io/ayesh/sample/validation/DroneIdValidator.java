package io.ayesh.sample.validation;

import io.ayesh.sample.repository.DroneRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class DroneIdValidator implements ConstraintValidator<DroneIdConstraint, Integer> {
    private final DroneRepository droneRepository;

    @Autowired
    public DroneIdValidator(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    @Override
    public boolean isValid(Integer droneId, ConstraintValidatorContext constraintValidatorContext) {
        return droneRepository.droneExistsById(droneId);
    }
}
