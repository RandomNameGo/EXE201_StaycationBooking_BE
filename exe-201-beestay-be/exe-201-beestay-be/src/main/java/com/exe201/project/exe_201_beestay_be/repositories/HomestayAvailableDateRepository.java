package com.exe201.project.exe_201_beestay_be.repositories;

import com.exe201.project.exe_201_beestay_be.models.HomestayAvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface HomestayAvailableDateRepository extends JpaRepository<HomestayAvailableDate, Integer> {

    @Query("select h.availableDate from HomestayAvailableDate h where h.homestay.id = :id")
    List<LocalDate> findByHomestayId(int id);
}