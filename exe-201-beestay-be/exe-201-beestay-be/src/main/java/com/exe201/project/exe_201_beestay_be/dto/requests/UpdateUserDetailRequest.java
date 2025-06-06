package com.exe201.project.exe_201_beestay_be.dto.requests;

import com.exe201.project.exe_201_beestay_be.dto.responses.UserDetailResponse;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserDetailRequest {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String gender;
    private LocalDate birthDate;

    private UserDetailResponse.AddressResponse addressResponse;

    @Data
    public static class AddressResponse {
        private String street;
        private String district;
        private String city;
        private String province;
    }
}
