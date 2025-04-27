package com.oraz.saken.demo.dto.service_item;

import java.math.BigDecimal;
import java.time.Duration;

public class ServiceItemResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer durationInMinutes;

    public ServiceItemResponse() {
    }

    public ServiceItemResponse(Long id, String name, String description, BigDecimal price, Integer durationInMinutes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.durationInMinutes = durationInMinutes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }
}
