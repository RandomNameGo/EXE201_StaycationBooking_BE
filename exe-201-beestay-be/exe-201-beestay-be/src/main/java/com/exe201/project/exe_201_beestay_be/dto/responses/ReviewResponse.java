package com.exe201.project.exe_201_beestay_be.dto.responses;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewResponse {
    private int userId;
    private String name;
    private float rating;
    private String comment;
    private LocalDate date;
}
