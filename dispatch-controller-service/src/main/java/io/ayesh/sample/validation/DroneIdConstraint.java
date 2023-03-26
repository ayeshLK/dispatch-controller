package io.ayesh.sample.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = DroneIdValidator.class)
public @interface DroneIdConstraint {
    String message() default "Invalid drone-id value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
