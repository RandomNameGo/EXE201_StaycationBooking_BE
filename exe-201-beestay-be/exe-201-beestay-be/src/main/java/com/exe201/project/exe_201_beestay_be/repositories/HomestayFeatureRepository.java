package com.exe201.project.exe_201_beestay_be.repositories;

import com.exe201.project.exe_201_beestay_be.models.HomestayFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HomestayFeatureRepository extends JpaRepository<HomestayFeature, Integer> {

    @Query("select h.featureName from HomestayFeature h where h.homestay.id = :id")
    List<String> findByHomestayId(int id);

    @Query("select h from HomestayFeature h where h.homestay.id = :id")
    List<HomestayFeature> findHomestayFeatureByHomestayId(int id);

}