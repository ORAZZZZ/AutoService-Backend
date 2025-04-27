package com.oraz.saken.demo.dto.appointment;

import jakarta.validation.constraints.NotNull;

public class AssignMechanicRequest {
    @NotNull(message = "ID механика обязателен")
    private Long mechanicId;

    public AssignMechanicRequest() {
    }

    public AssignMechanicRequest(Long mechanicId) {
        this.mechanicId = mechanicId;
    }

    public Long getMechanicId() {
        return mechanicId;
    }

    public void setMechanicId(Long mechanicId) {
        this.mechanicId = mechanicId;
    }
}
