package com.rincentral.test.application_runners;

import com.rincentral.test.models.CarFullInfo;
import com.rincentral.test.models.external.ExternalBrand;
import com.rincentral.test.models.external.ExternalCarInfo;
import com.rincentral.test.models.external.enums.EngineType;
import com.rincentral.test.models.external.enums.FuelType;
import com.rincentral.test.models.external.enums.GearboxType;
import com.rincentral.test.models.external.enums.WheelDriveType;
import com.rincentral.test.repositories.RepositoryInterface;
import com.rincentral.test.services.ExternalCarsApiServicePaginationBased;
import com.rincentral.test.utils.mapers.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class InitLoader implements ApplicationRunner {
    private static final String BODY_STYLES_SPLIT_REGEX = ", {2}";

    private final Mapper<CarFullInfo, ExternalCarInfo, ExternalBrand> carMapper;
    private final ExternalCarsApiServicePaginationBased externalCarsApiService;
    private final RepositoryInterface<CarFullInfo> carRepository;
    private final RepositoryInterface<GearboxType> gearboxTypeRepository;
    private final RepositoryInterface<EngineType> engineTypeRepository;
    private final RepositoryInterface<WheelDriveType> wheelDriveTypeRepository;
    private final RepositoryInterface<FuelType> fuelTypeRepository;
    private final RepositoryInterface<String> bodyTypeRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var brands = externalCarsApiService.loadAllBrands()
                .stream()
                .collect(Collectors.toMap(ExternalBrand::getId, e -> e));

        carRepository.saveAll(
                externalCarsApiService.loadAllCars()
                        .stream()
                        .map(e -> externalCarsApiService.loadCarInformationById(e.getId()))
                        .collect(Collectors.toMap(e -> (ExternalCarInfo) e, e -> brands.get(e.getBrandId())))
                        .entrySet()
                        .stream()
                        .map(e -> carMapper.map(e.getKey(), e.getValue()))
                        .collect(Collectors.toSet())
        );

        carRepository.getAll()
                .stream()
                .map(e -> e.getBody().getBodyStyle().split(BODY_STYLES_SPLIT_REGEX))
                .forEach(e -> {
                    for (String s : e) bodyTypeRepository.save(s);
                });

        engineTypeRepository.saveAll(
                carRepository.getAll()
                .stream()
                .map(e -> e.getEngine().getEngineType())
                .collect(Collectors.toSet())
        );

        fuelTypeRepository.saveAll(
                carRepository.getAll()
                .stream()
                .map(e -> e.getEngine().getFuelType())
                .collect(Collectors.toSet())
        );

        gearboxTypeRepository.saveAll(
                carRepository.getAll()
                .stream()
                .map(CarFullInfo::getGearboxType)
                .collect(Collectors.toSet())
        );

        wheelDriveTypeRepository.saveAll(
                carRepository.getAll()
                .stream()
                .map(CarFullInfo::getWheelDriveType)
                .collect(Collectors.toSet())
        );
    }
}
