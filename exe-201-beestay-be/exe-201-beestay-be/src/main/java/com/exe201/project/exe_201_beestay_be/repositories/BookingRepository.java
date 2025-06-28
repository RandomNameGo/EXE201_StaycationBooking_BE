package com.exe201.project.exe_201_beestay_be.repositories;

import com.exe201.project.exe_201_beestay_be.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select b from Booking b where b.user.id = :userId")
    List<Booking> findByBookingByUserId(int userId);

    @Query("select b from Booking b where b.homestay.host.id = :hostId")
    List<Booking> findByBookingByHostId(int hostId);

    @Query("select b from Booking b where b.homestay.id = :homestayId and b.user.id = :userId")
    List<Booking> findByHomestayIdAndUserId(int homestayId, int userId);

    @Query("select b from Booking b where b.homestay.id = :homestayId")
    List<Booking> findByHomestayId(int homestayId);
}