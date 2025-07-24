package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.dto.DashboardResponse;

public interface DashboardService {
    DashboardResponse getDashboardStatistics(String month, String year);
}