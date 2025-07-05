package com.exe201.project.exe_201_beestay_be.dto.responses;


import lombok.Data;

import java.util.List;

@Data
public class HostStayCationResponse {
    private int id;
    private String name;
    private String phone;
    private String email;
    private List<StayCationResponse> response;
    private long totalBooking;

    @Data
    public static class StayCationResponse {
        private int id;
        private String name;
        private LocationResponse location;
        private List<BookingResponse> booking;
    }
}
