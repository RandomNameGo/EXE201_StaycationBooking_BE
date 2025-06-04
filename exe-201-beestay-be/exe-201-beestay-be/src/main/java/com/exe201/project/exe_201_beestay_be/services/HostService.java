package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.dto.responses.HostDetailResponse;

public interface HostService {

    HostDetailResponse getHostDetail(int id);

    HostDetailResponse getHostDetailByAccountId(int accountId);
}
