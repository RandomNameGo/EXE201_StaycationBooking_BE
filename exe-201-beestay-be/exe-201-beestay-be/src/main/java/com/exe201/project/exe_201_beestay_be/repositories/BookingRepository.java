package com.exe201.project.exe_201_beestay_be.repositories;

import com.exe201.project.exe_201_beestay_be.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select b from Booking b where b.user.id = :userId and b.status = 'BOOKED'")
    List<Booking> findByBookingByUserId(int userId);

    @Query("select b from Booking b where b.homestay.host.id = :hostId and b.status = 'BOOKED'")
    List<Booking> findByBookingByHostId(int hostId);

    @Query("select b from Booking b where b.homestay.id = :homestayId and b.user.id = :userId")
    List<Booking> findByHomestayIdAndUserId(int homestayId, int userId);

    @Query("select b from Booking b where b.homestay.id = :homestayId and b.status != 'DISCARDED' and b.status != 'CANCELED'")
    List<Booking> findByHomestayId(int homestayId);

    @Query("select count(b) from Booking b where b.homestay.host.id = :hostId")
    Long countByHost(int hostId);

    @Query("select b from Booking b where b.homestay.id = :homestayId")
    List<Booking> findBookingByHomestayId(int homestayId);

    // Dashboard specific queries with date filtering
    @Query("select b from Booking b where b.status = 'CHECKED_IN' and b.createdAt >= :startDate and b.createdAt <= :endDate")
    List<Booking> findCheckedInBookingsByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("select count(distinct b.user.id) from Booking b where b.status = 'CHECKED_IN' and b.createdAt >= :startDate and b.createdAt <= :endDate")
    Long countDistinctCustomersWithCheckedInBookings(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("select count(b) from Booking b where b.status = 'CHECKED_IN' and b.createdAt >= :startDate and b.createdAt <= :endDate")
    Long countCheckedInBookingsByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("select sum(b.totalPrice) from Booking b where b.status = 'CHECKED_IN' and b.createdAt >= :startDate and b.createdAt <= :endDate")
    java.math.BigDecimal sumRevenueByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("select b from Booking b where b.homestay.id = :homestayId and b.status = 'CHECKED_IN' and b.createdAt >= :startDate and b.createdAt <= :endDate")
    List<Booking> findCheckedInBookingsByHomestayAndDateRange(@Param("homestayId") Integer homestayId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}