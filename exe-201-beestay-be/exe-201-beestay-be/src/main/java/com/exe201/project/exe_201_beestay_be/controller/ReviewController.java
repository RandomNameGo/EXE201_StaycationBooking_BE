package com.exe201.project.exe_201_beestay_be.controller;

import com.exe201.project.exe_201_beestay_be.dto.requests.StayCationReviewRequest;
import com.exe201.project.exe_201_beestay_be.exceptions.BadRequestException;
import com.exe201.project.exe_201_beestay_be.exceptions.StayCationNotFoundException;
import com.exe201.project.exe_201_beestay_be.exceptions.UserNotFoundException;
import com.exe201.project.exe_201_beestay_be.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bee-stay/api/v1")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/review/add")
    public ResponseEntity<?> addReview(@RequestBody StayCationReviewRequest review,
                                       @RequestParam("accountId") int accountId,
                                       @RequestParam("stayCationId") int stayCationId) {
        try{
            return ResponseEntity.ok().body(reviewService.addReview(review, accountId, stayCationId));
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        } catch (StayCationNotFoundException e) {
            throw new StayCationNotFoundException(e.getMessage());
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
