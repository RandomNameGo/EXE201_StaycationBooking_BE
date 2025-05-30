package com.exe201.project.exe_201_beestay_be.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VerifyOtpRequest {
    @NotNull
    private String userName;

    @NotNull
    private String password;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String role;

    @NotNull
    private String otp;
}
