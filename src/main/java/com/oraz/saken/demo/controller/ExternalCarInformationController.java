package com.oraz.saken.demo.controller;

import com.oraz.saken.demo.dto.external.CarInformationResponse;
import com.oraz.saken.demo.service.ExternalCarInformationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/external/cars")
public class ExternalCarInformationController {
    private final ExternalCarInformationService externalCarInformationService;

    public ExternalCarInformationController(ExternalCarInformationService externalCarInformationService) {
        this.externalCarInformationService = externalCarInformationService;
    }

    @GetMapping
    public ResponseEntity<List<CarInformationResponse>> searchCarByModel(@RequestParam String model) {
        List<CarInformationResponse> cars = externalCarInformationService.searchCarByModel(model);

        return ResponseEntity.ok(cars);
    }
}
