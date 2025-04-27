package com.oraz.saken.demo.dto.appointment;

import com.oraz.saken.demo.dto.car.CarResponse;
import com.oraz.saken.demo.dto.service_item.ServiceItemResponse;
import com.oraz.saken.demo.entity.AppointmentStatus;

import java.time.LocalDateTime;

public class AppointmentResponse {
    private Long id;
    private LocalDateTime appointmentTime;
    private AppointmentStatus status;
    private CarResponse car;
    private ServiceItemResponse service;
    private String mechanicName;

    public AppointmentResponse() {
    }

    public AppointmentResponse(Long id, LocalDateTime appointmentTime, AppointmentStatus status, CarResponse car, ServiceItemResponse service, String mechanicName) {
        this.id = id;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.car = car;
        this.service = service;
        this.mechanicName = mechanicName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public CarResponse getCar() {
        return car;
    }

    public void setCar(CarResponse car) {
        this.car = car;
    }

    public ServiceItemResponse getService() {
        return service;
    }

    public void setService(ServiceItemResponse service) {
        this.service = service;
    }

    public String getMechanicName() {
        return mechanicName;
    }

    public void setMechanicName(String mechanicName) {
        this.mechanicName = mechanicName;
    }
}
