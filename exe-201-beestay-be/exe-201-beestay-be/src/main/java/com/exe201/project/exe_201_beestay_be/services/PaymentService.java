package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.dto.requests.CreateHostSubscriptionRequest;
import com.exe201.project.exe_201_beestay_be.dto.responses.PaymentSubscriptionResponse;
import com.exe201.project.exe_201_beestay_be.models.PaymentSubscription;

import java.util.List;

public interface PaymentService {

    void createPayment(CreateHostSubscriptionRequest createHostSubscriptionRequest, long id);

    void updatePayment(long id);

    void addHostSubscription (long id);

    List<PaymentSubscriptionResponse> getAllPaymentSubscriptions();
}
