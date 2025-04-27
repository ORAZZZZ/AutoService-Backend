package com.oraz.saken.demo.service;

import com.oraz.saken.demo.dto.external.CarInformationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ExternalCarInformationService {
    private final RestTemplate restTemplate;

    @Value("${external.url}")
    private String API_URL;

    @Value("${external.api-key}")
    private String API_KEY;

    public ExternalCarInformationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<CarInformationResponse> searchCarByModel(String model) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", API_KEY);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<CarInformationResponse[]> response = restTemplate.exchange(
                API_URL,
                HttpMethod.GET,
                entity,
                CarInformationResponse[].class,
                model
        );

        CarInformationResponse[] cars = response.getBody();

        return cars != null ? Arrays.asList(cars) : Collections.emptyList();
    }
}
