package com.exe201.project.exe_201_beestay_be.dto.requests;

import com.exe201.project.exe_201_beestay_be.dto.responses.AmenitiesResponse;
import com.exe201.project.exe_201_beestay_be.dto.responses.LocationResponse;
import com.exe201.project.exe_201_beestay_be.dto.responses.PoliciesResponse;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class StayCationCreateRequest {
    private String name;
    private int hostId;
    private int pricePerNight;
    private int originalPricePerNight;
    private int discountPercentage;
    private String image;
    private String videoTourUrl;
    private String description;
    private List<String> features;

    private String roomType;
    private int roomCount;
    private int maxGuests;
    private int bedCount;
    private int bathroomCount;

    private boolean isFlashSale;
    private boolean isAvailable;
    private boolean isInstantBook;
    private boolean isRecommended;

    private List<LocalDate> availableDates;
    private LocationResponse location;
    private double distanceToCenter;
    private AmenitiesResponse amenities;
    private PoliciesResponse policies;
}
