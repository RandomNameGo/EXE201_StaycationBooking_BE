package com.exe201.project.exe_201_beestay_be.dto.responses;

import lombok.Data;

@Data
public class HostResponse {
    private String name;
    private String phone;
    private String email;
    private double rating;
}
