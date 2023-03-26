package io.ayesh.sample.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = UniqueSerialNumberValidator.class)
public @interface SerialNumberConstraint {
    String message() default "Serial number should be unique";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
