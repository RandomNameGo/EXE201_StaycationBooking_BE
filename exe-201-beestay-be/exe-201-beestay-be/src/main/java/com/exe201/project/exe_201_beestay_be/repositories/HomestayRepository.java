package com.exe201.project.exe_201_beestay_be.repositories;

import com.exe201.project.exe_201_beestay_be.models.Homestay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HomestayRepository extends JpaRepository<Homestay, Integer> {

    @Query("select h.id from Homestay h where h.host.id = :hostId")
    List<Integer> findByHostId(int hostId);

    Homestay findTopByOrderByIdDesc();

}