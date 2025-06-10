package com.exe201.project.exe_201_beestay_be.dto.requests;

import com.exe201.project.exe_201_beestay_be.dto.responses.LocationResponse;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateHostDetailRequest {

    private String name;
    private String phone;

    private LocationResponse location;

    private String bio;
    private List<SocialLinksRequest> socialLinks;

    @Data
    public static class SocialLinksRequest {
        private String platform;
        private String url;
    }
}
