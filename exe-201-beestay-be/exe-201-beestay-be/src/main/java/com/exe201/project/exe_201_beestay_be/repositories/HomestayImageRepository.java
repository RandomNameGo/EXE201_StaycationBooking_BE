package com.exe201.project.exe_201_beestay_be.repositories;

import com.exe201.project.exe_201_beestay_be.models.HomestayImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HomestayImageRepository extends JpaRepository<HomestayImage, Integer> {

    @Query("select h.url from HomestayImage h where h.homestay.id = :id")
    List<String> findByHomestayId(int id);

}