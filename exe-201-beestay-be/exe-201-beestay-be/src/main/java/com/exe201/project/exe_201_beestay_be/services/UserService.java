package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.dto.responses.UserDetailResponse;

import java.util.List;

public interface UserService {

    UserDetailResponse getUserDetails(int id);

    UserDetailResponse getUserDetailsByAccount(int accountId);
}
