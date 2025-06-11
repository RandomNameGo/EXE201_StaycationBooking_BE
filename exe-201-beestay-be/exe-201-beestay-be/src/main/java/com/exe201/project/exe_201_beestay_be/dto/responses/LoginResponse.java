package com.exe201.project.exe_201_beestay_be.dto.responses;

import lombok.Data;

@Data
public class LoginResponse {
    private int accountId;
    private String userName;
    private String role;
    private String token;
    private String refreshToken;
}
