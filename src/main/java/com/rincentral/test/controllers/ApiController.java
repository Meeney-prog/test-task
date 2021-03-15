package com.rincentral.test.controllers;

import com.rincentral.test.models.CarInfo;
import com.rincentral.test.models.external.enums.EngineType;
import com.rincentral.test.models.external.enums.FuelType;
import com.rincentral.test.models.external.enums.GearboxType;
import com.rincentral.test.models.external.enums.WheelDriveType;
import com.rincentral.test.models.params.CarRequestParameters;
import com.rincentral.test.models.params.MaxSpeedRequestParameters;
import com.rincentral.test.services.AroundCarService;
import com.rincentral.test.services.CarFullInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {
    private final AroundCarService aroundCarService;
    private final CarFullInfoService carFullInfoService;

    @GetMapping("/cars")
    public ResponseEntity<Set<? extends CarInfo>> getCars(CarRequestParameters requestParameters) {
        return ResponseEntity.ok(carFullInfoService.getCars(requestParameters));
    }

    @GetMapping("/fuel-types")
    public ResponseEntity<Set<FuelType>> getFuelTypes() {
        return ResponseEntity.ok(aroundCarService.getFuelTypes());
    }

    @GetMapping("/body-styles")
    public ResponseEntity<Set<String>> getBodyStyles() {
        return ResponseEntity.ok(aroundCarService.getBodyTypes());
    }

    @GetMapping("/engine-types")
    public ResponseEntity<Set<EngineType>> getEngineTypes() {
        return ResponseEntity.ok(aroundCarService.getEngineTypes());
    }

    @GetMapping("/wheel-drives")
    public ResponseEntity<Set<WheelDriveType>> getWheelDrives() {
        return ResponseEntity.ok(aroundCarService.getWheelDriveTypes());
    }

    @GetMapping("/gearboxes")
    public ResponseEntity<Set<GearboxType>> getGearboxTypes() {
        return ResponseEntity.ok(aroundCarService.getGearboxTypes());
    }

    @GetMapping("/max-speed")
    public ResponseEntity<Double> getMaxSpeed(MaxSpeedRequestParameters requestParameters) {
        return ResponseEntity.ok(carFullInfoService.getMaxSpeed(requestParameters));
    }
}
