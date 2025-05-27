package com.exe201.project.exe_201_beestay_be.dto.responses;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class StayCationDetailResponse {
    private int id;
    private String name;
    private HostResponse host;
    private LocationResponse location;
    private double distanceToCenter;

    private double averageRating;
    private long reviewCount;
    private List<ReviewResponse> reviews;

    private int pricePerNight;
    private int originalPricePerNight;
    private int discountPercentage;

    private String image;
    private List<String> imageList;
    private String videoTourUrl;

    private String description;
    private List<String> features;
    private AmenitiesResponse amenities;

    private String roomType;
    private int roomCount;
    private int maxGuests;
    private int bedCount;
    private int bathroomCount;

    private PoliciesResponse policies;

    private boolean isFlashSale;
    private boolean isAvailable;
    private boolean isInstantBook;
    private boolean isRecommended;

    private List<LocalDate> availableDates;
}
