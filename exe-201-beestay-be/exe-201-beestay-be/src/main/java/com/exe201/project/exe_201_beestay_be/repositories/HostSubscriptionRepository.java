package com.exe201.project.exe_201_beestay_be.repositories;

import com.exe201.project.exe_201_beestay_be.models.HostSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HostSubscriptionRepository extends JpaRepository<HostSubscription, Long> {

    @Query("select hs from HostSubscription hs where hs.host.id = :hostId and hs.subscription.id = :subscriptionId and hs.isActive is true order by hs.id desc")
    Optional<HostSubscription> findByHostIdAndSubscription(int hostId, long subscriptionId);
    
    @Query("select hs from HostSubscription hs where hs.host.id = :hostId and hs.isActive = true and hs.endDate >= CURRENT_DATE order by hs.endDate desc")
    Optional<HostSubscription> findActiveSubscriptionByHostId(int hostId);
}