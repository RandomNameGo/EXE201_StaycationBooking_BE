package com.exe201.project.exe_201_beestay_be.controller;

import com.exe201.project.exe_201_beestay_be.dto.requests.LoginRequest;
import com.exe201.project.exe_201_beestay_be.dto.requests.RegisterAccountRequest;
import com.exe201.project.exe_201_beestay_be.dto.requests.VerifyOtpRequest;
import com.exe201.project.exe_201_beestay_be.services.AccountService;
import com.exe201.project.exe_201_beestay_be.services.EmailService;
import com.exe201.project.exe_201_beestay_be.services.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bee-stay/api/v1")
public class AuthController {

    private final AccountService accountService;

    private final OtpService otpService;

    private final EmailService emailService;

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody RegisterAccountRequest request) {

            String otp = otpService.generateOtp(request.getEmail());
            emailService.sendOtpEmail(request.getEmail(), otp);
            return ResponseEntity.ok("OTP has been sent to email. Please verify to complete registration.");

    }

    @PostMapping("/auth/verify")
    public ResponseEntity<?> verify(@RequestBody VerifyOtpRequest request) {
        try{
            String message = "";
            if(otpService.verifyOtp(request.getEmail(), request.getOtp())){
                message = accountService.register(request);
            }
            return ResponseEntity.ok().body(message);
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try{
            return ResponseEntity.ok().body(accountService.login(request.getUserName(), request.getPassword()));
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
