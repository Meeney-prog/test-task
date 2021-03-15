package com.rincentral.test.services;

import com.rincentral.test.models.external.ExternalBrand;
import com.rincentral.test.models.external.ExternalCar;
import com.rincentral.test.utils.HelperPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ExternalCarsApiServicePaginationBased extends ExternalCarsApiService {
    private static final Logger LOGGER = LogManager.getLogger(ExternalCarsApiService.class);

    private static final int DEFAULT_SIZE = 20;

    private static final String ALL_CARS_URL = String.format(
            "http://localhost:8084/api/v1/cars/paged?size=%d&page=", DEFAULT_SIZE);

    private static final String ALL_BRANDS_URL = String.format(
            "http://localhost:8084/api/v1/brands/paged?size=%d&page=", DEFAULT_SIZE);

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<ExternalCar> loadAllCars() {
        var responseType = new ParameterizedTypeReference<HelperPage<ExternalCar>>() {};
        return getExternalResource(responseType, ALL_CARS_URL);
    }

    @Override
    public List<ExternalBrand> loadAllBrands() {
        var responseType = new ParameterizedTypeReference<HelperPage<ExternalBrand>>() {};
        return getExternalResource(responseType, ALL_BRANDS_URL);
    }

    private <T> List<T> getExternalResource(ParameterizedTypeReference<HelperPage<T>> responseType, String url) {
        int page = 0;
        var result = new ArrayList<T>();
        ResponseEntity<HelperPage<T>> response;
        try {
            do {
                response = restTemplate.exchange(url + page, HttpMethod.GET, null, responseType);
                result.addAll(response.getBody().getContent());
                page++;
            } while (response.getBody().hasNext());
            return result;
        } catch (RestClientException | NullPointerException e) {
            LOGGER.error("Error when trying to load page: " + url + page, e);
            return Collections.emptyList();
        }
    }
}
