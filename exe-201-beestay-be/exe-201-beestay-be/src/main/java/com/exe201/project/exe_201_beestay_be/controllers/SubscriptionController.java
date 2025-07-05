package com.exe201.project.exe_201_beestay_be.controllers;

import com.exe201.project.exe_201_beestay_be.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bee-stay/api/v1")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/subscription")
    public ResponseEntity<?> getSubscriptions() {
        try{
            return ResponseEntity.ok(subscriptionService.getSubscriptions());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
