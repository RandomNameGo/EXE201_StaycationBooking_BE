package com.exe201.project.exe_201_beestay_be.repositories;

import com.exe201.project.exe_201_beestay_be.models.HomestayPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HomestayPolicyRepository extends JpaRepository<HomestayPolicy, Integer> {
    @Query("select h from HomestayPolicy h where h.homestay.id = :id")
    HomestayPolicy findByHomestayId(int id);
}