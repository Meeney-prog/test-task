package com.rincentral.test.services;

import com.rincentral.test.exceptions.ItemNotFoundException;
import com.rincentral.test.models.CarFullInfo;
import com.rincentral.test.models.CarInfo;
import com.rincentral.test.models.params.CarRequestParameters;
import com.rincentral.test.models.params.MaxSpeedRequestParameters;
import com.rincentral.test.repositories.RepositoryInterface;
import com.rincentral.test.utils.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarFullInfoService {
    private final static String CONTAINS_IGNORE_CASE_REGEX = "(?i).*%s.*";
    private final static String YEAR_RANGE_SPLIT_REGEX = "-";
    private final RepositoryInterface<CarFullInfo> carFullInfoRepository;
    private final RequestValidator<MaxSpeedRequestParameters> maxSpeedParamValidator;
    private final RequestValidator<CarRequestParameters> carRequestParamValidator;

    public Set<? extends CarInfo> getCars(CarRequestParameters params) {
        carRequestParamValidator.validate(params);
        Predicate<CarFullInfo> predicate = e -> true;

        if (params.getCountry() != null && !params.getCountry().isBlank()) {
            predicate = predicate.and(e -> e.getCountry().equalsIgnoreCase(params.getCountry()));
        }

        if (params.getSegment() != null && !params.getSegment().isBlank()) {
            predicate = predicate.and(e -> e.getSegment().equalsIgnoreCase(params.getSegment()));
        }

        if (params.getMinEngineDisplacement() != null) {
            predicate = predicate.and(e -> e.getEngine().getEngineDisplacement() >= params.getMinEngineDisplacement());
        }

        if (params.getMinEngineHorsepower() != null) {
            predicate = predicate.and(e -> e.getEngine().getHp() >= params.getMinEngineHorsepower());
        }

        if (params.getMinMaxSpeed() != null) {
            predicate = predicate.and(e -> e.getMaxSpeed() >= params.getMinMaxSpeed());
        }

        if (params.getSearch() != null && !params.getSearch().isBlank()) {
            predicate = predicate.and(e -> e.getModel().matches(String.format(CONTAINS_IGNORE_CASE_REGEX, params.getSearch()))
                    || e.getModification().matches(String.format(CONTAINS_IGNORE_CASE_REGEX, params.getSearch()))
                    || e.getGeneration().matches(String.format(CONTAINS_IGNORE_CASE_REGEX, params.getSearch())));
        }

        if (params.getYear() != null) {
            predicate = predicate.and(
                    e -> (params.getYear() >= Integer.parseInt(e.getYearsRange().split(YEAR_RANGE_SPLIT_REGEX)[0])
                            && params.getYear() <= (e.getYearsRange().split(YEAR_RANGE_SPLIT_REGEX)[1].equalsIgnoreCase("present")
                            ? LocalDate.now().getYear()
                            : Integer.parseInt(e.getYearsRange().split(YEAR_RANGE_SPLIT_REGEX)[1])))
            );
        }

        if (params.getBodyStyle() != null && !params.getBodyStyle().isBlank()) {
            predicate = predicate.and(e -> e.getBody().getBodyStyle().matches(String.format(CONTAINS_IGNORE_CASE_REGEX, params.getBodyStyle())));
        }

        var result = carFullInfoRepository.search(predicate);

        if (result.isEmpty()) {
            throw new ItemNotFoundException("There are no cars matching your request");
        }

        if (params.getIsFull() != null && params.getIsFull()) {
            return result;
        }

        return result
                .stream()
                .map(this::toCarInfo)
                .collect(Collectors.toSet());
    }

    public Double getMaxSpeed(MaxSpeedRequestParameters params) {
        maxSpeedParamValidator.validate(params);
        Predicate<CarFullInfo> predicate = e -> true;

        if (params.getBrand() != null && !params.getBrand().isBlank()) {
            predicate = predicate.and((e) -> e.getBrand().equals(params.getBrand()));
        } else if (params.getModel() != null && !params.getModel().isBlank()) {
            predicate = predicate.and((e) -> e.getModel().equals(params.getModel()));
        }

        var cars = carFullInfoRepository.search(predicate);
        if (cars.isEmpty()) {
            throw new ItemNotFoundException("There are no cars matching your request");
        }

        return cars.stream().mapToInt(CarFullInfo::getMaxSpeed).sum() / (double) cars.size();
    }

    private CarInfo toCarInfo(CarFullInfo car) {
        return CarInfo
                .builder()
                .id(car.getId())
                .segment(car.getSegment())
                .brand(car.getBrand())
                .model(car.getModel())
                .country(car.getCountry())
                .generation(car.getGeneration())
                .modification(car.getModification())
                .build();
    }
}
