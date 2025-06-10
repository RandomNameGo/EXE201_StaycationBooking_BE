package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.dto.requests.UpdateUserDetailRequest;
import com.exe201.project.exe_201_beestay_be.dto.responses.UserDetailResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    UserDetailResponse getUserDetails(int id);

    UserDetailResponse getUserDetailsByAccount(int accountId);

    String updateUserDetails(UpdateUserDetailRequest request, int accountId);

    String updateUserAvatar(MultipartFile avatar, int accountId) throws IOException;
}
