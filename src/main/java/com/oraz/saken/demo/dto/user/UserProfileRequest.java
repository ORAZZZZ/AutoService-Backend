package com.oraz.saken.demo.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public class UserProfileRequest {
    @NotBlank(message = "Номер телефона не должен быть пустым")
    private String phoneNumber;

    @NotBlank(message = "Адрес не должен быть пустым")
    private String address;

    @NotNull(message = "Дата рождения обязательна для заполнения")
    @PastOrPresent(message = "Дата рождения должна быть в прошлом или настоящем")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    public UserProfileRequest() {
    }

    public UserProfileRequest(String phoneNumber, String address, LocalDate birthDate) {
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
