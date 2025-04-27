package com.oraz.saken.demo.controller;

import com.oraz.saken.demo.dto.car.CarRequest;
import com.oraz.saken.demo.dto.car.CarResponse;
import com.oraz.saken.demo.service.CarService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<CarResponse>> getCars(Principal principal) {
        String email = principal.getName();

        return ResponseEntity.ok(carService.getUserCars(email));
    }

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CarResponse> createCar(@RequestBody @Valid CarRequest request, Principal principal) {
        String email = principal.getName();

        return ResponseEntity.ok(carService.createCar(request, email));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CarResponse> updateCar(@PathVariable Long id, @RequestBody @Valid CarRequest request, Principal principal) {
        String email = principal.getName();

        return ResponseEntity.ok(carService.updateCar(id, request, email));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id, Principal principal) {
        String email = principal.getName();

        carService.deleteCar(id, email);

        return ResponseEntity.noContent().build();
    }
}
