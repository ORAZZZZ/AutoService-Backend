package com.oraz.saken.demo.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class ReplaceRolesRequest {
    @NotEmpty(message = "Список ролей не может быть пустым")
    private List<@NotBlank(message = "Название роли не может быть пустым") String> roles;

    public ReplaceRolesRequest() {
    }

    public ReplaceRolesRequest(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
