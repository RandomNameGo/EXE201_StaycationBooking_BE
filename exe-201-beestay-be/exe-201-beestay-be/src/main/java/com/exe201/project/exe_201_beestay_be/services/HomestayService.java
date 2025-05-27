package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.dto.responses.StayCationDetailResponse;

import java.util.List;

public interface HomestayService {

    List<StayCationDetailResponse> getAllRoomDetails();
}
