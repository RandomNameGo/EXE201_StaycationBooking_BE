package com.exe201.project.exe_201_beestay_be.repositories;

import com.exe201.project.exe_201_beestay_be.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query("select r from RefreshToken r where r.token = :refreshToken")
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}