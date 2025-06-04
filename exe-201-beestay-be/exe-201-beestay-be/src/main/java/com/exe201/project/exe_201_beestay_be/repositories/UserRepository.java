package com.exe201.project.exe_201_beestay_be.repositories;

import com.exe201.project.exe_201_beestay_be.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByProviderAndProviderId(String provider, String providerId);

    @Query("select u from User u where u.account.id = :accountId")
    Optional<User> findByAccountId(int accountId);
}