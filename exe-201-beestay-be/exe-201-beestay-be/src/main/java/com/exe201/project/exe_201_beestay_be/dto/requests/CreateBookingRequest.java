package com.exe201.project.exe_201_beestay_be.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CreateBookingRequest {
    @NotNull
    private int accountId;

    @NotNull
    private int homestayId;

    @NotNull
    @Size(max = 11)
    private String phoneNumber;

    @NotNull
    @Size(max = 100)
    private String fullName;

    @NotNull
    private LocalDateTime checkIn;

    @NotNull
    private LocalDateTime checkOut;

    @Size(max = 50)
    private String paymentMethod;

    @NotNull
    private BigDecimal totalPrice;
}
