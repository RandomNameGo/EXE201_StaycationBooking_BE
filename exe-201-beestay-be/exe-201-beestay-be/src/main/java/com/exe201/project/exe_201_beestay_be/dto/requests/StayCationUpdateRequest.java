package com.exe201.project.exe_201_beestay_be.dto.requests;

import com.exe201.project.exe_201_beestay_be.dto.responses.AmenitiesResponse;
import com.exe201.project.exe_201_beestay_be.dto.responses.LocationResponse;
import com.exe201.project.exe_201_beestay_be.dto.responses.PoliciesResponse;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class StayCationUpdateRequest {
    private String name;
    private Integer pricePerNight;
    private Integer originalPricePerNight;
    private Integer discountPercentage;
    private String image;
    private String videoTourUrl;
    private String description;
    private List<String> features;

    private String roomType;
    private Integer roomCount;
    private Integer maxGuests;
    private Integer bedCount;
    private Integer bathroomCount;

    private Boolean isFlashSale;
    private Boolean isAvailable;
    private Boolean isInstantBook;
    private Boolean isRecommended;

    private List<LocalDate> availableDates;
    private LocationResponse location;
    private Double distanceToCenter;
    private AmenitiesResponse amenities;
    private PoliciesResponse policies;
}
