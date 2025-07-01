package com.exe201.project.exe_201_beestay_be.repositories;

import com.exe201.project.exe_201_beestay_be.models.PaymentSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentSubscriptionRepository extends JpaRepository<PaymentSubscription, Long> {
}