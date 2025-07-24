package com.exe201.project.exe_201_beestay_be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    private Long totalCustomers;
    private Long totalBookings;
    private BigDecimal totalRevenue;
    private Long totalHomestays;
    private Long totalHosts;
    private Long totalSubscriptions;
    private Long totalReviews;
    private List<TopHomestayDto> topHomestays;
    private PeriodDto period;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopHomestayDto {
        private String homestayName;
        private String hostName;
        private Long bookingCount;
        private BigDecimal revenue;
        private Float averageRating;
        private LocationDto location;
        private Integer reviewCount;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LocationDto {
        private String address;
        private String district;
        private String city;
        private String province;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PeriodDto {
        private String month;
        private String year;
    }
}