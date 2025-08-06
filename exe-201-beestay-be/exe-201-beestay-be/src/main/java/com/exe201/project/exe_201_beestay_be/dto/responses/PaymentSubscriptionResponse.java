package com.exe201.project.exe_201_beestay_be.dto.responses;

import lombok.Data;

@Data
public class PaymentSubscriptionResponse {
    private long transactionId;
    private String fullName;
    private String transactionName;
    private String transactionValue;
    private String transactionDate;
}
