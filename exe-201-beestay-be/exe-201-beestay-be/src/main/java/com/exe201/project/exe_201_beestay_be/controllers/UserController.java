package com.exe201.project.exe_201_beestay_be.controllers;

import com.exe201.project.exe_201_beestay_be.dto.requests.UpdateUserDetailRequest;
import com.exe201.project.exe_201_beestay_be.exceptions.UserNotFoundException;
import com.exe201.project.exe_201_beestay_be.services.BookingService;
import com.exe201.project.exe_201_beestay_be.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bee-stay/api/v1")
public class UserController {
    private final UserService userService;

    private final BookingService bookingService;

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable int id) {
        try{
            return ResponseEntity.ok().body(userService.getUserDetails(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/by-account/{accountId}")
    public ResponseEntity<?> getUserByAccount(@PathVariable int accountId) {
        try{
            return ResponseEntity.ok().body(userService.getUserDetailsByAccount(accountId));
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User not found");
        }
    }

    @PutMapping("/user/update/{accountId}")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDetailRequest request, @PathVariable int accountId) {
        try{
            return ResponseEntity.ok().body(userService.updateUserDetails(request, accountId));
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User not found");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/user/update-avatar/{accountId}")
    public ResponseEntity<?> updateAvatar(@RequestParam("image") MultipartFile file, @PathVariable int accountId) throws IOException {
        try{
            return ResponseEntity.ok().body(userService.updateUserAvatar(file, accountId));
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User not found");
        }
    }

    @GetMapping("/user/booking")
    public ResponseEntity<?> getUserBooking(@RequestParam int accountId) {
        try{
            return ResponseEntity.ok().body(bookingService.viewBookingByUser(accountId));
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User not found");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
