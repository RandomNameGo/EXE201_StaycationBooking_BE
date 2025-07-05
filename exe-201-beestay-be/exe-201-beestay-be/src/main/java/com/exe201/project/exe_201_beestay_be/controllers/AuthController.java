package com.exe201.project.exe_201_beestay_be.controllers;

import com.exe201.project.exe_201_beestay_be.dto.requests.LoginRequest;
import com.exe201.project.exe_201_beestay_be.dto.requests.VerifyOtpRequest;
import com.exe201.project.exe_201_beestay_be.exceptions.AccountNotValidException;
import com.exe201.project.exe_201_beestay_be.exceptions.RefreshTokenBotValidException;
import com.exe201.project.exe_201_beestay_be.services.AccountService;
import com.exe201.project.exe_201_beestay_be.services.EmailService;
import com.exe201.project.exe_201_beestay_be.services.OtpService;
import com.exe201.project.exe_201_beestay_be.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bee-stay/api/v1")
public class AuthController {

    private final AccountService accountService;

    private final OtpService otpService;

    private final EmailService emailService;

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/auth/verify")
    public ResponseEntity<?> register(@RequestParam("email") String email) {

            String otp = otpService.generateOtp(email);
            emailService.sendOtpEmail(email, otp);
            return ResponseEntity.ok("OTP has been sent to email. Please verify to complete registration.");

    }

    @PostMapping("/auth/register")
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
        try {
            return ResponseEntity.ok().body(accountService.login(request.getUserName(), request.getPassword()));
        } catch (AccountNotValidException e){
            throw new AccountNotValidException("Account not valid");
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/auth/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestParam("refresh_token") String refreshToken) {
        try{
            return ResponseEntity.ok().body(refreshTokenService.createNewToken(refreshToken));
        } catch (RefreshTokenBotValidException e){
            throw new RefreshTokenBotValidException("Refresh token not valid");
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
