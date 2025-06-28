package com.exe201.project.exe_201_beestay_be.controller;

import com.exe201.project.exe_201_beestay_be.dto.requests.CreateBookingRequest;
import com.exe201.project.exe_201_beestay_be.exceptions.HostNotFoundException;
import com.exe201.project.exe_201_beestay_be.exceptions.UserNotFoundException;
import com.exe201.project.exe_201_beestay_be.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bee-stay/api/v1")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/booking/create")
    public ResponseEntity<?> createBooking(@RequestBody CreateBookingRequest booking) {
        try {
            return ResponseEntity.ok().body(bookingService.addBooking(booking));
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        } catch (HostNotFoundException e){
            throw new HostNotFoundException(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/booking/cancel/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId) {
        try {
            return ResponseEntity.ok().body(bookingService.cancelBooking(bookingId));
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/booking/discard/{bookingId}")
    public ResponseEntity<?> discardBooking(@PathVariable Long bookingId) {
        try {
            return ResponseEntity.ok().body(bookingService.discardBooking(bookingId));
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
