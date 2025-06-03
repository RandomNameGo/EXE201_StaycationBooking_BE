package com.exe201.project.exe_201_beestay_be.dto.requests;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StayCationReviewRequest {
    private float rating;
    private String comment;
    private LocalDate reviewDate;
}
