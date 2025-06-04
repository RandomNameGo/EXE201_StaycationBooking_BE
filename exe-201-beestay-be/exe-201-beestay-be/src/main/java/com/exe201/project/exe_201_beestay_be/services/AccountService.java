package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.dto.requests.RegisterAccountRequest;
import com.exe201.project.exe_201_beestay_be.dto.requests.VerifyOtpRequest;
import com.exe201.project.exe_201_beestay_be.dto.responses.LoginResponse;

public interface AccountService {

    LoginResponse login(String userName, String password);

    String register(VerifyOtpRequest register);
}
