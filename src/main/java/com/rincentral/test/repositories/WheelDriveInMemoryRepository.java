package com.rincentral.test.repositories;

import com.rincentral.test.models.external.enums.WheelDriveType;
import org.springframework.stereotype.Repository;

@Repository
public class WheelDriveInMemoryRepository extends AbstractInMemoryRepository<WheelDriveType> {
}
