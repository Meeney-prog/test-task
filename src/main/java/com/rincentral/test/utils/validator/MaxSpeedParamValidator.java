package com.rincentral.test.utils.validator;

import com.rincentral.test.exceptions.ValidationException;
import com.rincentral.test.models.params.MaxSpeedRequestParameters;
import org.springframework.stereotype.Component;

@Component
public class MaxSpeedParamValidator implements RequestValidator<MaxSpeedRequestParameters> {
    @Override
    public void validate(MaxSpeedRequestParameters params) {
        if ((params.getBrand() == null || params.getBrand().isBlank())
                && (params.getModel() == null || params.getModel().isBlank())) {
            throw new ValidationException("Both brand and model can't be blank at the same time");
        }

        if ((params.getBrand() != null && !params.getBrand().isBlank())
                && (params.getModel() != null && !params.getModel().isBlank())) {
            throw new ValidationException("Both brand and model can't be presented at the same time");
        }
    }
}
