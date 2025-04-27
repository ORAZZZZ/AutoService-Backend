package com.oraz.saken.demo.dto.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AppointmentRequest {
    @NotNull(message = "ID машины обязателен")
    private Long carId;

    @NotNull(message = "ID услуги обязателен")
    private Long serviceId;

    @NotNull(message = "Дата и время обязательны")
    @FutureOrPresent(message = "Нельзя записаться на прошедшее время")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime appointmentTime;

    public AppointmentRequest() {
    }

    public AppointmentRequest(Long carId, Long serviceId, LocalDateTime appointmentTime) {
        this.carId = carId;
        this.serviceId = serviceId;
        this.appointmentTime = appointmentTime;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
}
