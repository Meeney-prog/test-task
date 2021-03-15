package com.rincentral.test.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rincentral.test.models.external.enums.GearboxType;
import com.rincentral.test.models.external.enums.WheelDriveType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Getter
@SuperBuilder
public class CarFullInfo extends CarInfo {
    private final BodyCharacteristics body;
    private final EngineCharacteristics engine;

    @JsonIgnore
    private final GearboxType gearboxType;

    @JsonIgnore
    private final WheelDriveType wheelDriveType;

    @JsonIgnore
    private final Integer maxSpeed;

    @JsonIgnore
    private final Double acceleration;

    @JsonIgnore
    private final String yearsRange;
}
