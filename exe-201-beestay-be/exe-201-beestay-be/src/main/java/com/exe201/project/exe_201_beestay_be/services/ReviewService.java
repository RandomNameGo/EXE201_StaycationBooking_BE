package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.dto.requests.StayCationReviewRequest;

public interface ReviewService {
    String addReview(StayCationReviewRequest review, int userId, int stayCationId);

}
