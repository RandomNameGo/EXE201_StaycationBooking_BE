package com.exe201.project.exe_201_beestay_be.dto.responses;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateHostSubscriptionRequest {
    private int accountId;
    private int subscriptionId;
    private String subscriptionName;
    private String subscriptionDescription;
    private BigDecimal price;
}
