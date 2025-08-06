package com.exe201.project.exe_201_beestay_be.dto.responses;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CreateBookingResponse {
    private int accountId;
    private int homestayId;
    private String phoneNumber;
    private String fullName;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private String paymentMethod;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
}
