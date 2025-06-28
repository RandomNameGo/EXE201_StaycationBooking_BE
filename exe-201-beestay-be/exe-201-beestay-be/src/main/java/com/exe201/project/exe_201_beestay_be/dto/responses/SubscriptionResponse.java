package com.exe201.project.exe_201_beestay_be.dto.responses;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SubscriptionResponse {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer durationDays;
}
