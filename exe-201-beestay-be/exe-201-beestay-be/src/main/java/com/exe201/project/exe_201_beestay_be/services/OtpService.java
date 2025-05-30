package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.models.OtpVerification;
import com.exe201.project.exe_201_beestay_be.repositories.OtpVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {


    private final OtpVerificationRepository otpRepo;

    public String generateOtp(String email) {
        String otp = String.valueOf(new Random().nextInt(999999));
        OtpVerification otpVerification = new OtpVerification();
        otpVerification.setEmail(email);
        otpVerification.setOtp(otp);
        otpVerification.setExpirationTime((LocalDateTime.now().plusMinutes(5)));
        otpRepo.save(otpVerification);
        return otp;
    }

    public boolean verifyOtp(String email, String otp) {
        OtpVerification record = otpRepo.findTopByEmailOrderByExpirationTimeDesc(email);
        if (record != null && record.getOtp().equals(otp)
                && LocalDateTime.now().isBefore(record.getExpirationTime())) {
            return true;
        }
        return false;
    }
}
