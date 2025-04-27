package com.oraz.saken.demo.dto.car;

import jakarta.validation.constraints.NotBlank;

public class CarRequest {
    @NotBlank(message = "Бренд обязателен")
    private String brand;

    @NotBlank(message = "Модель обязательна")
    private String model;

    @NotBlank(message = "Номерной знак обязателен")
    private String licensePlate;

    public CarRequest() {
    }

    public CarRequest(String brand, String model, String licensePlate) {
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
