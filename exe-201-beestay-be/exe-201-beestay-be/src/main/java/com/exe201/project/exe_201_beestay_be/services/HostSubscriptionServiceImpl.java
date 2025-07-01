package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.exceptions.HostNotFoundException;
import com.exe201.project.exe_201_beestay_be.models.Host;
import com.exe201.project.exe_201_beestay_be.models.HostSubscription;
import com.exe201.project.exe_201_beestay_be.models.Subscription;
import com.exe201.project.exe_201_beestay_be.repositories.HostRepository;
import com.exe201.project.exe_201_beestay_be.repositories.HostSubscriptionRepository;
import com.exe201.project.exe_201_beestay_be.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HostSubscriptionServiceImpl implements HostSubscriptionService{

    private final HostSubscriptionRepository hostSubscriptionRepository;

    private final HostRepository hostRepository;

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public void createHostSubscription(int accountId, long subscriptionId) {
        Optional<Host> host = hostRepository.findByAccountId(accountId);
        if (host.isEmpty()) {
            throw new HostNotFoundException("Host not found");
        }
        Optional<Subscription> subscription = subscriptionRepository.findById(subscriptionId);
        if (subscription.isEmpty()) {
            throw new HostNotFoundException("Subscription not found");
        }

        int durationDays = subscription.get().getDurationDays();
        LocalDate endDate = LocalDate.now().plusDays(durationDays);

        HostSubscription hostSubscription = new HostSubscription();
        hostSubscription.setHost(host.get());
        hostSubscription.setSubscription(subscription.get());
        hostSubscription.setStartDate(LocalDate.now());
        hostSubscription.setEndDate(endDate);
        hostSubscription.setIsActive(true);
        hostSubscriptionRepository.save(hostSubscription);

    }

    @Override
    public boolean checkHostSubscription(int accountId, long subscriptionId) {

        Optional<Host> host = hostRepository.findByAccountId(accountId);
        if (host.isEmpty()) {
            throw new HostNotFoundException("Host not found");
        }

        Optional<HostSubscription> hostSubscription = hostSubscriptionRepository.findByHostIdAndSubscription(host.get().getId(), subscriptionId);
        return hostSubscription.isPresent();
    }
}
