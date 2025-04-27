package com.oraz.saken.demo.dto.appointment;

import com.oraz.saken.demo.entity.AppointmentStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateAppointmentStatusRequest {
    @NotNull(message = "Статус обязателен")
    private AppointmentStatus status;

    public UpdateAppointmentStatusRequest() {
    }

    public UpdateAppointmentStatusRequest(AppointmentStatus status) {
        this.status = status;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
}
