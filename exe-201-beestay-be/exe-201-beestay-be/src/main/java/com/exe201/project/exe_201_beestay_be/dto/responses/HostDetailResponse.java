package com.exe201.project.exe_201_beestay_be.dto.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class HostDetailResponse {
    private Integer id;
    private String name;
    private String phone;
    private String email;
    private String avatar;

    private LocationResponse location;

    private float averageHomestayRating;
    private List<Integer> homeStay;

    private LocalDate joinedDate;
    private boolean isSuperHost;
    private String status;

    private String bio;
    private List<SocialLinksResponse> socialLinks;

    @Data
    public static class SocialLinksResponse {
        private String platform;
        private String url;
    }
}
