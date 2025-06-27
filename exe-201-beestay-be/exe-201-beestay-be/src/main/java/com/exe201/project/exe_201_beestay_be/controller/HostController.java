package com.exe201.project.exe_201_beestay_be.controller;

import com.exe201.project.exe_201_beestay_be.dto.requests.UpdateHostDetailRequest;
import com.exe201.project.exe_201_beestay_be.dto.responses.HostDetailResponse;
import com.exe201.project.exe_201_beestay_be.exceptions.HostNotFoundException;
import com.exe201.project.exe_201_beestay_be.exceptions.UserNotFoundException;
import com.exe201.project.exe_201_beestay_be.services.BookingService;
import com.exe201.project.exe_201_beestay_be.services.HostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/bee-stay/api/v1")
@RequiredArgsConstructor
public class HostController {
    private final HostService hostService;

    private final BookingService bookingService;

    @GetMapping("/host/{id}")
    public ResponseEntity<HostDetailResponse> findById(@PathVariable int id) {
        try{
            return ResponseEntity.ok().body(hostService.getHostDetail(id));
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/host/by-account/{accountId}")
    public ResponseEntity<HostDetailResponse> getByAccountId(@PathVariable int accountId) {
        try{
            return ResponseEntity.ok().body(hostService.getHostDetailByAccountId(accountId));
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/host/update/{accountId}")
    public ResponseEntity<?> updateHostDetail(@RequestBody UpdateHostDetailRequest updateHostDetailRequest, int accountId) {
        try {
            return ResponseEntity.ok().body(hostService.updateHostDetail(updateHostDetailRequest, accountId));
        } catch (HostNotFoundException e){
            throw new HostNotFoundException(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/host/update-avatar/{hostId}")
    public ResponseEntity<?> updateHostAvatar(@RequestParam("image") MultipartFile image, @PathVariable int hostId) {
        try {
            return ResponseEntity.ok().body(hostService.updateAvatar(image, hostId));
        } catch (HostNotFoundException e){
            throw new HostNotFoundException(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/host/booking")
    public ResponseEntity<?> getUserBooking(@RequestParam int accountId) {
        try{
            return ResponseEntity.ok().body(bookingService.viewBookingByHost(accountId));
        } catch (UserNotFoundException e) {
            throw new HostNotFoundException("Host not found");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/host/booking/check-in/{bookingId}")
    public ResponseEntity<?> checkIn(@PathVariable long bookingId) {
        try{
            return ResponseEntity.ok().body(bookingService.checkInBooking(bookingId));
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("Booking not found");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }
}
