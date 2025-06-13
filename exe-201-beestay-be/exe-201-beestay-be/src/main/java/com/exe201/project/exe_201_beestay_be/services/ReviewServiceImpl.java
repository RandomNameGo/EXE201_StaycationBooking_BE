package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.dto.requests.StayCationReviewRequest;
import com.exe201.project.exe_201_beestay_be.exceptions.StayCationNotFoundException;
import com.exe201.project.exe_201_beestay_be.exceptions.UserNotFoundException;
import com.exe201.project.exe_201_beestay_be.models.Homestay;
import com.exe201.project.exe_201_beestay_be.models.Review;
import com.exe201.project.exe_201_beestay_be.models.User;
import com.exe201.project.exe_201_beestay_be.repositories.HomestayRepository;
import com.exe201.project.exe_201_beestay_be.repositories.ReviewRepository;
import com.exe201.project.exe_201_beestay_be.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    private final HomestayRepository homestayRepository;

    @Override
    public String addReview(StayCationReviewRequest review, int accountId, int stayCationId) {

        Optional<User> user = userRepository.findByAccountId(accountId);
        Optional<Homestay> homestay = homestayRepository.findById(stayCationId);
        if(user.isEmpty()) {
            throw new UserNotFoundException("user not found");
        }
        if(homestay.isEmpty()) {
            throw new StayCationNotFoundException("stay cation not found");
        }

        Review reviewToAdd = new Review();
        reviewToAdd.setUser(user.get());
        reviewToAdd.setHomestay(homestay.get());
        reviewToAdd.setRating(review.getRating());
        reviewToAdd.setComment(review.getComment());
        reviewToAdd.setReviewDate(review.getReviewDate());
        reviewRepository.save(reviewToAdd);
        calculateAverageRating(homestay.get());
        countReview(user.get());
        return "Review added successfully";

    }

    private void calculateAverageRating(Homestay homestay) {
        double averageRating;
        double sumRating = 0.0;
        long totalReviews = reviewRepository.countByHomestayId(homestay.getId());
        List<Review> reviews = reviewRepository.findByHomestayId(homestay.getId());
        for(Review review : reviews) {
            sumRating += review.getRating();
        }

        averageRating = sumRating / totalReviews;
        homestay.setAverageRating((float) averageRating);
        homestayRepository.save(homestay);
    }

    private void countReview(User user){
        int reviewCount;
        long totalReviews = reviewRepository.countByUserId(user.getId());
        reviewCount = (int) totalReviews;
        user.setReviewCount(reviewCount);
        userRepository.save(user);
    }
}
