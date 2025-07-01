package com.exe201.project.exe_201_beestay_be.services;


import com.exe201.project.exe_201_beestay_be.models.HostSubscription;

public interface HostSubscriptionService {

    void createHostSubscription(int accountId, long subscriptionId);

    boolean checkHostSubscription(int accountId, long subscriptionId);
}
