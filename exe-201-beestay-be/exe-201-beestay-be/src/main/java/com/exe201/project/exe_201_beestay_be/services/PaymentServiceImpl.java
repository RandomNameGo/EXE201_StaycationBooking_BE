package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.dto.requests.CreateHostSubscriptionRequest;
import com.exe201.project.exe_201_beestay_be.exceptions.HostNotFoundException;
import com.exe201.project.exe_201_beestay_be.exceptions.UserNotFoundException;
import com.exe201.project.exe_201_beestay_be.models.Host;
import com.exe201.project.exe_201_beestay_be.models.PaymentSubscription;
import com.exe201.project.exe_201_beestay_be.models.Subscription;
import com.exe201.project.exe_201_beestay_be.repositories.HostRepository;
import com.exe201.project.exe_201_beestay_be.repositories.PaymentSubscriptionRepository;
import com.exe201.project.exe_201_beestay_be.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentSubscriptionRepository PaymentSubscriptionRepository;

    private final HostRepository hostRepository;

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public void createPayment(CreateHostSubscriptionRequest createHostSubscriptionRequest, long id) {

        Optional<Host> host = hostRepository.findByAccountId(createHostSubscriptionRequest.getAccountId());
        if (host.isEmpty()) {
            throw new HostNotFoundException("Host not found");
        }
        Optional<Subscription> subscription = subscriptionRepository.findById(createHostSubscriptionRequest.getSubscriptionId());
        if (subscription.isEmpty()) {
            throw new HostNotFoundException("Subscription not found");
        }
        PaymentSubscription paymentSubscription = new PaymentSubscription();
        paymentSubscription.setId(id);
        paymentSubscription.setProductName(subscription.get().getName());
        paymentSubscription.setHost(host.get());
        paymentSubscription.setSubscription(subscription.get());
        paymentSubscription.setPrice(createHostSubscriptionRequest.getPrice().intValue());
        paymentSubscription.setDescription(createHostSubscriptionRequest.getSubscriptionDescription());
        paymentSubscription.setPaymentStatus("PENDING");
        paymentSubscription.setPaymentDate(Instant.now());
        paymentSubscription.setTransactionId(null);
        PaymentSubscriptionRepository.save(paymentSubscription);
    }

    @Override
    public void updatePayment(long id) {
        Optional<PaymentSubscription> paymentSubscription = PaymentSubscriptionRepository.findById(id);
        if (paymentSubscription.isEmpty()) {
            throw new UserNotFoundException("Order not found");
        }
        paymentSubscription.get().setPaymentStatus("SUCCESS");
    }
}
