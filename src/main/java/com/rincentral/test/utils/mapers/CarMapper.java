package com.rincentral.test.utils.mapers;

import com.rincentral.test.models.BodyCharacteristics;
import com.rincentral.test.models.CarFullInfo;
import com.rincentral.test.models.EngineCharacteristics;
import com.rincentral.test.models.external.ExternalBrand;
import com.rincentral.test.models.external.ExternalCarInfo;
import org.springframework.stereotype.Component;

@Component
public class CarMapper implements Mapper<CarFullInfo, ExternalCarInfo, ExternalBrand> {
    @Override
    public CarFullInfo map(ExternalCarInfo car, ExternalBrand brand) {
        return CarFullInfo
                .builder()
                .id(car.getId())
                .segment(car.getSegment())
                .brand(brand.getTitle())
                .model(car.getModel())
                .country(brand.getCountry())
                .generation(car.getGeneration())
                .modification(car.getModification())
                .body(
                        BodyCharacteristics
                                .builder()
                                .bodyHeight(car.getBodyHeight())
                                .bodyLength(car.getBodyLength())
                                .bodyStyle(car.getBodyStyle())
                                .bodyWidth(car.getBodyWidth())
                                .build()
                )
                .engine(
                        EngineCharacteristics
                                .builder()
                                .engineDisplacement(car.getEngineDisplacement())
                                .engineType(car.getEngineType())
                                .fuelType(car.getFuelType())
                                .hp(car.getHp())
                                .build()
                )
                .gearboxType(car.getGearboxType())
                .wheelDriveType(car.getWheelDriveType())
                .maxSpeed(car.getMaxSpeed())
                .acceleration(car.getAcceleration())
                .yearsRange(car.getYearsRange())
                .build();
    }
}
