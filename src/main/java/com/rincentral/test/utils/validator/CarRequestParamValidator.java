package com.rincentral.test.utils.validator;

import com.rincentral.test.exceptions.ValidationException;
import com.rincentral.test.models.params.CarRequestParameters;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component()
public class CarRequestParamValidator implements RequestValidator<CarRequestParameters> {
    private final static int YEAR_FIRST_CAR_WAS_MADE = 1885;

    @Override
    public void validate(CarRequestParameters params) {
        if (params.getMinEngineDisplacement() != null && params.getMinEngineDisplacement() <= 0) {
            throw new ValidationException("minEngineDisplacement should be positive");
        }

        if (params.getMinEngineHorsepower() != null && params.getMinEngineHorsepower() <= 0) {
            throw new ValidationException("minEngineHorsepower should be positive");
        }

        if (params.getMinMaxSpeed() != null && params.getMinMaxSpeed() <= 0) {
            throw new ValidationException("minMaxSpeed should be positive");
        }

        if (params.getYear() != null && params.getYear() < YEAR_FIRST_CAR_WAS_MADE) {
            throw new ValidationException("First car wasn't even made by so far");
        }

        if (params.getYear() != null && params.getYear() > LocalDate.now().getYear()) {
            throw new ValidationException("You are requesting a car from the future");
        }
    }
}
