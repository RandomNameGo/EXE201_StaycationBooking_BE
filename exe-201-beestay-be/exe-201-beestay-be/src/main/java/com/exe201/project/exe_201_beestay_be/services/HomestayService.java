package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.dto.requests.StayCationCreateRequest;
import com.exe201.project.exe_201_beestay_be.dto.requests.StayCationUpdateRequest;
import com.exe201.project.exe_201_beestay_be.dto.responses.StayCationDetailResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface HomestayService {

    List<StayCationDetailResponse> getAllRoomDetails();

    String addStayCation(StayCationCreateRequest stayCationCreateRequest, int accountId);

    String uploadHomestayImage(List<MultipartFile> files, int homeStayId) throws IOException;

    String updateStayCation(int homeStayId, StayCationUpdateRequest stayCationUpdateRequest);

    List<StayCationDetailResponse> getByHost(int hostAccountId);

    String uploadStayCationVideo(MultipartFile file, int homeStayId) throws IOException;

    StayCationDetailResponse getStayCation(int homeStayId);
}
