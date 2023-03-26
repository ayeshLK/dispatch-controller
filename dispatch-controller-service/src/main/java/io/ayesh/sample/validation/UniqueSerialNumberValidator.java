package io.ayesh.sample.validation;

import io.ayesh.sample.repository.DroneRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueSerialNumberValidator implements ConstraintValidator<SerialNumberConstraint, String> {
    private final DroneRepository droneRepository;

    @Autowired
    public UniqueSerialNumberValidator(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    @Override
    public boolean isValid(String serialNumber, ConstraintValidatorContext context) {
        return !droneRepository.droneExistsBySerialNumber(serialNumber);
    }
}
