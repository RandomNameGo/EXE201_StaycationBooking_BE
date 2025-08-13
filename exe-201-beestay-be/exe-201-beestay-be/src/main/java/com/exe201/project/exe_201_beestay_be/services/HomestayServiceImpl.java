package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.dto.requests.StayCationCreateRequest;
import com.exe201.project.exe_201_beestay_be.dto.requests.StayCationUpdateRequest;
import com.exe201.project.exe_201_beestay_be.dto.responses.*;
import com.exe201.project.exe_201_beestay_be.exceptions.HostNotFoundException;
import com.exe201.project.exe_201_beestay_be.exceptions.StayCationNotFoundException;
import com.exe201.project.exe_201_beestay_be.models.*;
import com.exe201.project.exe_201_beestay_be.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private final HostRepository hostRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public List<StayCationDetailResponse> getAllRoomDetails() {
        List<StayCationDetailResponse> stayCationDetailResponses = new ArrayList<>();
        List<Homestay> homestays = homestayRepository.findAll();
        return getStayCationDetailResponses(stayCationDetailResponses, homestays);
    }

    @Override
    public String addStayCation(StayCationCreateRequest request, int accountId) {

        Homestay homestay = new Homestay();


        Host host = hostRepository.findByAccountId(accountId)
                .orElseThrow(() -> new RuntimeException("Host not found with ID: " + accountId));
        homestay.setHost(host);

        homestay.setName(request.getName());

        homestay.setAverageRating(0f);

        homestay.setReviewCount(0);

        homestay.setAddress(request.getLocation().getAddress());
        homestay.setDistrict(request.getLocation().getDistrict());
        homestay.setCity(request.getLocation().getCity());
        homestay.setProvince(request.getLocation().getProvince());

        homestay.setPricePerNight(request.getPricePerNight());
        homestay.setOriginalPricePerNight(request.getOriginalPricePerNight());
        homestay.setDiscountPercentage(request.getDiscountPercentage());

        homestay.setVideoTourUrl(request.getVideoTourUrl());
        homestay.setDescription(request.getDescription());

        homestay.setRoomType(request.getRoomType());
        homestay.setRoomCount(request.getRoomCount());
        homestay.setMaxGuests(request.getMaxGuests());
        homestay.setBedCount(request.getBedCount());
        homestay.setBathroomCount(request.getBathroomCount());

        homestay.setIsFlashSale(false);
        homestay.setIsAvailable(true);
        homestay.setIsInstantBook(true);
        homestay.setIsRecommended(false);

        homestay.setDistanceToCenter(request.getDistanceToCenter());

        homestayRepository.save(homestay);

        Homestay tempHomestay = homestayRepository.findTopByOrderByIdDesc();
        HomestayAmenity amenities = new HomestayAmenity();

        amenities.setHomestay(tempHomestay);

        amenities.setWifi(request.getAmenities().isWifi());
        amenities.setAirConditioner(request.getAmenities().isAirConditioner());
        amenities.setKitchen(request.getAmenities().isKitchen());
        amenities.setPrivateBathroom(request.getAmenities().isPrivateBathroom());
        amenities.setPool(request.getAmenities().isPool());
        amenities.setPetAllowed(request.getAmenities().isPetAllowed());
        amenities.setParking(request.getAmenities().isParking());
        amenities.setBalcony(request.getAmenities().isBalcony());
        amenities.setBbqArea(request.getAmenities().isBbqArea());
        amenities.setRoomService(request.getAmenities().isRoomService());
        amenities.setSecurityCamera(request.getAmenities().isSecurityCamera());

        amenityRepository.save(amenities);

        for (String homestayFeature : request.getFeatures()){
            HomestayFeature tempHomestayFeature = new HomestayFeature();
            tempHomestayFeature.setHomestay(tempHomestay);
            tempHomestayFeature.setFeatureName(homestayFeature);
            featureRepository.save(tempHomestayFeature);
        }

        for (LocalDate availableDate : request.getAvailableDates()){
            HomestayAvailableDate tempHomestayAvailableDate = new HomestayAvailableDate();
            tempHomestayAvailableDate.setHomestay(tempHomestay);
            tempHomestayAvailableDate.setAvailableDate(availableDate);
            availableDateRepository.save(tempHomestayAvailableDate);
        }

        HomestayPolicy policy = new HomestayPolicy();

        policy.setHomestay(homestay);
        policy.setIsRefundable(request.getPolicies().isRefundable());
        policy.setAllowPet(request.getPolicies().isAllowPet());
        policy.setAllowSmoking(request.getPolicies().isAllowSmoking());
        policyRepository.save(policy);

        return "Add StayCation Successfully";
    }

    @Override
    public String uploadHomestayImage(List<MultipartFile> files, int homeStayId) throws IOException {

        if(!homestayRepository.existsById(homeStayId)){
            throw new StayCationNotFoundException("Stay Cation Not Found");
        }

        Homestay homestay = homestayRepository.findById(homeStayId).get();

        for (MultipartFile file : files){
            String imgUrl = cloudinaryService.uploadFile(file);
            HomestayImage homestayImage = new HomestayImage();
            homestayImage.setHomestay(homestay);
            homestayImage.setUrl(imgUrl);
            imageRepository.save(homestayImage);
        }
        return "Uploaded Successfully";
    }

    @Override
    public String updateStayCation(int homeStayId, StayCationUpdateRequest request) {
        Homestay homestay = homestayRepository.findById(homeStayId)
                .orElseThrow(() -> new RuntimeException("Homestay not found with ID: " + homeStayId));


        if (request.getName() != null && !request.getName().isBlank()) homestay.setName(request.getName());
        if (request.getLocation() != null) {
            if (request.getLocation().getAddress() != null) homestay.setAddress(request.getLocation().getAddress());
            if (request.getLocation().getDistrict() != null) homestay.setDistrict(request.getLocation().getDistrict());
            if (request.getLocation().getCity() != null) homestay.setCity(request.getLocation().getCity());
            if (request.getLocation().getProvince() != null) homestay.setProvince(request.getLocation().getProvince());
        }

        if (request.getPricePerNight() != null) homestay.setPricePerNight(request.getPricePerNight());
        if (request.getOriginalPricePerNight() != null) homestay.setOriginalPricePerNight(request.getOriginalPricePerNight());
        if (request.getDiscountPercentage() != null) homestay.setDiscountPercentage(request.getDiscountPercentage());
        if (request.getVideoTourUrl() != null) homestay.setVideoTourUrl(request.getVideoTourUrl());
        if (request.getDescription() != null) homestay.setDescription(request.getDescription());

        if (request.getRoomType() != null) homestay.setRoomType(request.getRoomType());
        if (request.getRoomCount() != null) homestay.setRoomCount(request.getRoomCount());
        if (request.getMaxGuests() != null) homestay.setMaxGuests(request.getMaxGuests());
        if (request.getBedCount() != null) homestay.setBedCount(request.getBedCount());
        if (request.getBathroomCount() != null) homestay.setBathroomCount(request.getBathroomCount());

        if (request.getIsFlashSale() != null) homestay.setIsFlashSale(request.getIsFlashSale());
        if (request.getIsAvailable() != null) homestay.setIsAvailable(request.getIsAvailable());
        if (request.getIsInstantBook() != null) homestay.setIsInstantBook(request.getIsInstantBook());
        if (request.getIsRecommended() != null) homestay.setIsRecommended(request.getIsRecommended());

        if (request.getDistanceToCenter() != null) homestay.setDistanceToCenter(request.getDistanceToCenter());

        homestayRepository.save(homestay);

        // Update amenities
        HomestayAmenity amenities = amenityRepository.findByHomestayId(homeStayId);


        if (request.getAmenities() != null) {
            amenities.setWifi(request.getAmenities().isWifi());
            amenities.setAirConditioner(request.getAmenities().isAirConditioner());
            amenities.setKitchen(request.getAmenities().isKitchen());
            amenities.setPrivateBathroom(request.getAmenities().isPrivateBathroom());
            amenities.setPool(request.getAmenities().isPool());
            amenities.setPetAllowed(request.getAmenities().isPetAllowed());
            amenities.setParking(request.getAmenities().isParking());
            amenities.setBalcony(request.getAmenities().isBalcony());
            amenities.setBbqArea(request.getAmenities().isBbqArea());
            amenities.setRoomService(request.getAmenities().isRoomService());
            amenities.setSecurityCamera(request.getAmenities().isSecurityCamera());
        }

        amenityRepository.save(amenities);

        if (request.getFeatures() != null && !request.getFeatures().isEmpty()) {
            List<HomestayFeature> features = featureRepository.findHomestayFeatureByHomestayId(homeStayId);
            featureRepository.deleteAll(features);
            for (String feature : request.getFeatures()) {
                HomestayFeature hf = new HomestayFeature();
                hf.setHomestay(homestay);
                hf.setFeatureName(feature);
                featureRepository.save(hf);
            }
        }

        if (request.getAvailableDates() != null && !request.getAvailableDates().isEmpty()) {
            List<HomestayAvailableDate> homestayAvailableDates = availableDateRepository.findHomestayAvailableDateByHomestayId(homeStayId);
            availableDateRepository.deleteAll(homestayAvailableDates);
            for (LocalDate date : request.getAvailableDates()) {
                HomestayAvailableDate had = new HomestayAvailableDate();
                had.setHomestay(homestay);
                had.setAvailableDate(date);
                availableDateRepository.save(had);
            }
        }

        HomestayPolicy policy = policyRepository.findByHomestayId(homeStayId);
        policy.setHomestay(homestay);

        if (request.getPolicies() != null) {
            policy.setIsRefundable(request.getPolicies().isRefundable());
            policy.setAllowPet(request.getPolicies().isAllowPet());
            policy.setAllowSmoking(request.getPolicies().isAllowSmoking());
        }

        policyRepository.save(policy);

        return "Update StayCation Successfully";
    }

    @Override
    public List<StayCationDetailResponse> getByHost(int hostAccountId) {

        Optional<Host> host = hostRepository.findByAccountId(hostAccountId);

        if(host.isEmpty()) {
            throw new HostNotFoundException("Host not found");
        }

        Host hostEntity = host.get();
        List<StayCationDetailResponse> stayCationDetailResponses = new ArrayList<>();
        List<Homestay> homestays = homestayRepository.findHomestayByHostId(hostEntity.getId());
        return getStayCationDetailResponses(stayCationDetailResponses, homestays);
    }

    @Override
    public String uploadStayCationVideo(MultipartFile file, int homeStayId) throws IOException {
        if(!homestayRepository.existsById(homeStayId)){
            throw new StayCationNotFoundException("Stay Cation Not Found");
        }

        Homestay homestay = homestayRepository.findById(homeStayId).get();

        String videoUrl = cloudinaryService.uploadVideo(file);
        homestay.setVideoTourUrl(videoUrl);
        homestayRepository.save(homestay);

        return "Uploaded Successfully";
    }

    @Override
    public StayCationDetailResponse getStayCation(int homeStayId) {
        Optional<Homestay> tempHomestay = homestayRepository.findById(homeStayId);
        if(tempHomestay.isEmpty()) {
            throw new StayCationNotFoundException("Stay Cation Not Found");
        }
        Homestay homestay = tempHomestay.get();
        StayCationDetailResponse stayCationDetailResponse = new StayCationDetailResponse();
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

        stayCationDetailResponse.setRoomType(homestay.getRoomType());

        stayCationDetailResponse.setRoomCount(homestay.getRoomCount());

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
        return stayCationDetailResponse;
    }

    @Override
    public String deleteImage(String imageURL) {
        HomestayImage homestayImage = imageRepository.findByURL(imageURL);
        imageRepository.delete(homestayImage);
        return "Deleted Successfully";
    }

    private List<StayCationDetailResponse> getStayCationDetailResponses(List<StayCationDetailResponse> stayCationDetailResponses, List<Homestay> homestays) {
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

            stayCationDetailResponse.setRoomType(homestay.getRoomType());

            stayCationDetailResponse.setRoomCount(homestay.getRoomCount());

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
            stayCationDetailResponses.add(stayCationDetailResponse);
        }
        return stayCationDetailResponses;
    }

}
