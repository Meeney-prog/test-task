package com.rincentral.test.repositories;

import com.rincentral.test.models.external.enums.GearboxType;
import org.springframework.stereotype.Repository;

@Repository
public class GearboxInMemoryRepository extends AbstractInMemoryRepository<GearboxType> {
}
