package com.rincentral.test.services;

import com.rincentral.test.exceptions.ItemNotFoundException;
import com.rincentral.test.models.external.enums.EngineType;
import com.rincentral.test.models.external.enums.FuelType;
import com.rincentral.test.models.external.enums.GearboxType;
import com.rincentral.test.models.external.enums.WheelDriveType;
import com.rincentral.test.repositories.RepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class AroundCarService {
    private final RepositoryInterface<GearboxType> gearboxTypeRepository;
    private final RepositoryInterface<EngineType> engineTypeRepository;
    private final RepositoryInterface<WheelDriveType> wheelDriveTypeRepository;
    private final RepositoryInterface<FuelType> fuelTypeRepository;
    private final RepositoryInterface<String> bodyTypeRepository;

    public Set<FuelType> getFuelTypes() {
        return checkIsEmpty(fuelTypeRepository.getAll());
    }

    public Set<GearboxType> getGearboxTypes() {
        return checkIsEmpty(gearboxTypeRepository.getAll());
    }

    public Set<EngineType> getEngineTypes() {
        return checkIsEmpty(engineTypeRepository.getAll());
    }

    public Set<String> getBodyTypes() {
        return checkIsEmpty(bodyTypeRepository.getAll());
    }

    public Set<WheelDriveType> getWheelDriveTypes() {
        return checkIsEmpty(wheelDriveTypeRepository.getAll());
    }

    private <T> Set<T> checkIsEmpty(Set<T> set) {
        if (set == null || set.isEmpty()) {
            throw new ItemNotFoundException("No such entity has been found");
        }
        return set;
    }
}
