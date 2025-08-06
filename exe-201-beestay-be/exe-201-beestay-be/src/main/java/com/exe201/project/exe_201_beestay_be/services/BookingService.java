package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.dto.requests.CreateBookingRequest;
import com.exe201.project.exe_201_beestay_be.dto.responses.BookingResponse;
import com.exe201.project.exe_201_beestay_be.dto.responses.CreateBookingResponse;


import java.util.List;

public interface BookingService {
    CreateBookingResponse addBooking(CreateBookingRequest booking);

    String cancelBooking(Long bookingId);

    List<BookingResponse> viewBookingByUser(int accountId);

    List<BookingResponse> viewBookingByHost(int accountId);

    String checkInBooking(Long bookingId);

    String discardBooking(Long bookingId);
}
