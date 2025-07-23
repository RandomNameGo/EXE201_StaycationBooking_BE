package com.exe201.project.exe_201_beestay_be.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HostDashboardResponse {
    private Integer totalCustomers;
    private Integer totalBookings;
    private BigDecimal revenue;
    private Integer totalHomestays;
    private List<TopHomestayResponse> topHomestays;
    private List<PeriodResponse> period;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopHomestayResponse {
        private Integer id;
        private String name;
        private LocationResponse location;
        private Float averageRating;
        private Integer reviewCount;
        private Integer pricePerNight;
        private String image;
        private Long totalBooking;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LocationResponse {
        private String address;
        private String district;
        private String city;
        private String province;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PeriodResponse {
        private Integer month;
        private Integer year;
    }
}