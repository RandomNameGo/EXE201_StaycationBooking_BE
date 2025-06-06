package com.exe201.project.exe_201_beestay_be.dto.responses;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDetailResponse {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String gender;
    private LocalDate birthDate;

    private AddressResponse addressResponse;

    private LocalDate joinedDate;
    private int currentBooking;
    private int totalBookingSuccess;

    private List<Integer> favoriteHomestay;
    private int reviewCount;

    private boolean isVerified;
    private String status;

    @Data
    public static class AddressResponse {
        private String street;
        private String district;
        private String city;
        private String province;
    }
}
