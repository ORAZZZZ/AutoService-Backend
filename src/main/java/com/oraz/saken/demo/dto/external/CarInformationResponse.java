package com.oraz.saken.demo.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CarInformationResponse {
    @JsonProperty("class")
    private String carClass;

    private Integer cylinders;
    private Double displacement;
    private String drive;

    @JsonProperty("fuel_type")
    private String fuelType;

    private String make;
    private String model;
    private String transmission;
    private Integer year;

    public CarInformationResponse() {
    }

    public CarInformationResponse(String carClass, Integer cylinders, Double displacement, String drive, String fuelType, String make, String model, String transmission, Integer year) {
        this.carClass = carClass;
        this.cylinders = cylinders;
        this.displacement = displacement;
        this.drive = drive;
        this.fuelType = fuelType;
        this.make = make;
        this.model = model;
        this.transmission = transmission;
        this.year = year;
    }

    public String getCarClass() {
        return carClass;
    }

    public void setCarClass(String carClass) {
        this.carClass = carClass;
    }

    public Integer getCylinders() {
        return cylinders;
    }

    public void setCylinders(Integer cylinders) {
        this.cylinders = cylinders;
    }

    public Double getDisplacement() {
        return displacement;
    }

    public void setDisplacement(Double displacement) {
        this.displacement = displacement;
    }

    public String getDrive() {
        return drive;
    }

    public void setDrive(String drive) {
        this.drive = drive;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
