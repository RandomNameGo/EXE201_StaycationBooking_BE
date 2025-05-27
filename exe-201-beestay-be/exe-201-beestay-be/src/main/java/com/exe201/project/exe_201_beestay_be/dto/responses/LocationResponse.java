package com.exe201.project.exe_201_beestay_be.dto.responses;

import lombok.Data;

@Data
public class LocationResponse {
    private String address;
    private String district;
    private String city;
    private String province;
}
