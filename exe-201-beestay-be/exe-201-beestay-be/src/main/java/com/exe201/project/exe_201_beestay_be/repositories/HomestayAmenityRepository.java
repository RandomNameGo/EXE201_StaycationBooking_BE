package com.exe201.project.exe_201_beestay_be.repositories;

import com.exe201.project.exe_201_beestay_be.models.HomestayAmenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HomestayAmenityRepository extends JpaRepository<HomestayAmenity, Integer> {

    @Query("select h from HomestayAmenity h where h.homestay.id = :id")
    HomestayAmenity findByHomestayId(int id);
}