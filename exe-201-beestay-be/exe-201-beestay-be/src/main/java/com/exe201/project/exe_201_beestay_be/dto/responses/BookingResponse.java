package com.exe201.project.exe_201_beestay_be.dto.responses;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BookingResponse {
    private long bookingId;
    private String homestay;
    private String phoneNumber;
    private String fullName;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private String paymentMethod;
    private BigDecimal totalPrice;
}
