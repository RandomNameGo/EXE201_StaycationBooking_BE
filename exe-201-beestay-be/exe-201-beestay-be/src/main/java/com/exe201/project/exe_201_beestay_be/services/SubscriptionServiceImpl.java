package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.dto.responses.SubscriptionResponse;
import com.exe201.project.exe_201_beestay_be.models.Subscription;
import com.exe201.project.exe_201_beestay_be.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService{

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public List<SubscriptionResponse> getSubscriptions() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        List<SubscriptionResponse> subscriptionResponses = new ArrayList<>();
        for (Subscription subscription : subscriptions) {
            SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
            subscriptionResponse.setId(subscription.getId());
            subscriptionResponse.setName(subscription.getName());
            subscriptionResponse.setPrice(subscription.getPrice());
            subscriptionResponse.setDescription(subscription.getDescription());
            subscriptionResponse.setDurationDays(subscription.getDurationDays());
            subscriptionResponses.add(subscriptionResponse);
        }
        return subscriptionResponses;
    }
}
