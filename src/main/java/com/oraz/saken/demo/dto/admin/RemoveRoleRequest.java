package com.oraz.saken.demo.dto.admin;

import jakarta.validation.constraints.NotBlank;

public class RemoveRoleRequest {
    @NotBlank(message = "Название роли не может быть пустым")
    private String role;

    public RemoveRoleRequest() {
    }

    public RemoveRoleRequest(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
