package com.exe201.project.exe_201_beestay_be.dto.requests;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateHostSubscriptionRequest {
    private int accountId;
    private long subscriptionId;
    private String subscriptionName;
    private String subscriptionDescription;
    private BigDecimal price;
    private String returnUrl;
    private String cancelUrl;
}
