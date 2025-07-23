package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.dto.requests.UpdateHostDetailRequest;
import com.exe201.project.exe_201_beestay_be.dto.responses.HostDetailResponse;
import com.exe201.project.exe_201_beestay_be.dto.responses.HostStayCationResponse;
import com.exe201.project.exe_201_beestay_be.dto.responses.HostDashboardResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface HostService {

    HostDetailResponse getHostDetail(int id);

    HostDetailResponse getHostDetailByAccountId(int accountId);

    String updateHostDetail(UpdateHostDetailRequest request, int accountId);

    String updateAvatar(MultipartFile file, int accountId) throws IOException;

    List<HostStayCationResponse> getHostStayCation();

    HostDashboardResponse getHostDashboard(int accountId, Integer month, Integer year);
}
