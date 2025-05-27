package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.dto.responses.*;
import com.exe201.project.exe_201_beestay_be.models.*;
import com.exe201.project.exe_201_beestay_be.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomestayServiceImpl implements HomestayService {

    private final HomestayRepository homestayRepository;
    private final HomestayImageRepository imageRepository;
    private final HomestayFeatureRepository featureRepository;
    private final HomestayPolicyRepository policyRepository;
    private final HomestayAmenityRepository amenityRepository;
    private final HomestayAvailableDateRepository availableDateRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public List<StayCationDetailResponse> getAllRoomDetails() {
        List<StayCationDetailResponse> stayCationDetailRespons = new ArrayList<>();
        List<Homestay> homestays = homestayRepository.findAll();
        for (Homestay homestay : homestays) {
            StayCationDetailResponse stayCationDetailResponse = new StayCationDetailResponse();
            //id
            stayCationDetailResponse.setId(homestay.getId());
            //name
            stayCationDetailResponse.setName(homestay.getName());

            stayCationDetailResponse.setDescription(homestay.getDescription());

            stayCationDetailResponse.setAvailable(homestay.getIsAvailable());

            stayCationDetailResponse.setAverageRating(homestay.getAverageRating());

            stayCationDetailResponse.setVideoTourUrl(homestay.getVideoTourUrl());

            stayCationDetailResponse.setBedCount(homestay.getBedCount());

            stayCationDetailResponse.setBathroomCount(homestay.getBathroomCount());

            stayCationDetailResponse.setPricePerNight(homestay.getPricePerNight());

            stayCationDetailResponse.setOriginalPricePerNight(homestay.getOriginalPricePerNight());

            stayCationDetailResponse.setFlashSale(homestay.getIsFlashSale());

            stayCationDetailResponse.setDiscountPercentage(homestay.getDiscountPercentage());

            stayCationDetailResponse.setRoomCount(homestay.getRoomCount());

            stayCationDetailResponse.setRoomCount(stayCationDetailResponse.getRoomCount());

            stayCationDetailResponse.setMaxGuests(homestay.getMaxGuests());

            stayCationDetailResponse.setInstantBook(homestay.getIsInstantBook());

            stayCationDetailResponse.setRecommended(homestay.getIsRecommended());

            //host
            HostResponse hostResponse = new HostResponse();
            Host host = homestay.getHost();
            hostResponse.setName(host.getName());
            hostResponse.setEmail(host.getEmail());
            hostResponse.setPhone(host.getPhone());
            hostResponse.setRating(host.getAverageRating());
            stayCationDetailResponse.setHost(hostResponse);

            //amenity
            AmenitiesResponse amenitiesResponse = new AmenitiesResponse();
            HomestayAmenity homestayAmenity = amenityRepository.findByHomestayId(homestay.getId());
            amenitiesResponse.setAirConditioner(homestayAmenity.getAirConditioner());
            amenitiesResponse.setParking(homestayAmenity.getParking());
            amenitiesResponse.setKitchen(homestayAmenity.getKitchen());
            amenitiesResponse.setRoomService(homestayAmenity.getRoomService());
            amenitiesResponse.setBalcony(homestayAmenity.getBalcony());
            amenitiesResponse.setPool(homestayAmenity.getPool());
            amenitiesResponse.setWifi(homestayAmenity.getWifi());
            amenitiesResponse.setPetAllowed(homestayAmenity.getPetAllowed());
            amenitiesResponse.setSecurityCamera(homestayAmenity.getSecurityCamera());
            amenitiesResponse.setPrivateBathroom(homestayAmenity.getPrivateBathroom());
            amenitiesResponse.setBbqArea(homestayAmenity.getBbqArea());
            stayCationDetailResponse.setAmenities(amenitiesResponse);

            //Policy
            PoliciesResponse policiesResponse = new PoliciesResponse();
            HomestayPolicy homestayPolicy = policyRepository.findByHomestayId(homestay.getId());
            policiesResponse.setAllowPet(homestayPolicy.getAllowPet());
            policiesResponse.setRefundable(homestayPolicy.getIsRefundable());
            policiesResponse.setAllowSmoking(homestayPolicy.getAllowSmoking());
            stayCationDetailResponse.setPolicies(policiesResponse);

            //Location
            LocationResponse locationResponse = new LocationResponse();
            locationResponse.setAddress(homestay.getAddress());
            locationResponse.setCity(homestay.getCity());
            locationResponse.setDistrict(homestay.getDistrict());
            locationResponse.setProvince(homestay.getProvince());
            stayCationDetailResponse.setLocation(locationResponse);

            //Distance to center
            stayCationDetailResponse.setDistanceToCenter(homestay.getDistanceToCenter());

            //Feature
            stayCationDetailResponse.setFeatures(featureRepository.findByHomestayId(homestay.getId()));

            //Image
            stayCationDetailResponse.setImageList(imageRepository.findByHomestayId(homestay.getId()));

            //Available Date
            stayCationDetailResponse.setAvailableDates(availableDateRepository.findByHomestayId(homestay.getId()));

            //Review
            List<ReviewResponse> reviewResponses = new ArrayList<>();
            List<Review> homestayReviews = reviewRepository.findByHomestayId(homestay.getId());
            for (Review review : homestayReviews ){
                ReviewResponse reviewResponse = new ReviewResponse();
                reviewResponse.setUserId(review.getUser().getId());
                reviewResponse.setName(review.getUser().getName());
                reviewResponse.setComment(review.getComment());
                reviewResponse.setRating(review.getRating());
                reviewResponse.setDate(review.getReviewDate());
                reviewResponses.add(reviewResponse);
            }
            stayCationDetailResponse.setReviews(reviewResponses);
            stayCationDetailResponse.setReviewCount(reviewRepository.countByHomestayId(homestay.getId()));
            stayCationDetailRespons.add(stayCationDetailResponse);
        }
        return stayCationDetailRespons;
    }
}
