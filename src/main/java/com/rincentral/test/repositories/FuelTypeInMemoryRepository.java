package com.rincentral.test.repositories;

import com.rincentral.test.models.external.enums.FuelType;
import org.springframework.stereotype.Repository;

@Repository
public class FuelTypeInMemoryRepository extends AbstractInMemoryRepository<FuelType> {
}
