package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.dto.DashboardResponse;
import com.exe201.project.exe_201_beestay_be.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final BookingRepository bookingRepository;
    private final HomestayRepository homestayRepository;
    private final HostRepository hostRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final HostSubscriptionRepository hostSubscriptionRepository;

    @Override
    public DashboardResponse getDashboardStatistics(String month, String year) {
        // Parse month and year for filtering
        YearMonth yearMonth = YearMonth.of(Integer.parseInt(year), Integer.parseInt(month));
        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        // Get statistics using custom queries with date filtering
        DashboardResponse response = new DashboardResponse();
        
        // Set period
        response.setPeriod(new DashboardResponse.PeriodDto(month, year));
        
        // Get total statistics with date filtering
        response.setTotalCustomers(getTotalCustomersWithSuccessfulBookings(startOfMonth, endOfMonth));
        response.setTotalBookings(getTotalSuccessfulBookings(startOfMonth, endOfMonth));
        response.setTotalRevenue(getTotalRevenue(startOfMonth, endOfMonth));
        response.setTotalHomestays((long) homestayRepository.findAll().size());
        response.setTotalHosts((long) hostRepository.findAll().size());
        response.setTotalSubscriptions((long) hostSubscriptionRepository.findAll().size());
        response.setTotalReviews((long) reviewRepository.findAll().size());
        
        // Get top homestays with date filtering
        response.setTopHomestays(getTopHomestays(startOfMonth, endOfMonth));
        
        return response;
    }

    private Long getTotalCustomersWithSuccessfulBookings(LocalDateTime startDate, LocalDateTime endDate) {
        // Count distinct users who have CHECKED_IN bookings within the date range using database query
        return bookingRepository.countDistinctCustomersWithCheckedInBookings(startDate, endDate);
    }

    private Long getTotalSuccessfulBookings(LocalDateTime startDate, LocalDateTime endDate) {
        // Count bookings with CHECKED_IN status within the date range using database query
        return bookingRepository.countCheckedInBookingsByDateRange(startDate, endDate);
    }

    private BigDecimal getTotalRevenue(LocalDateTime startDate, LocalDateTime endDate) {
        // Sum total price of CHECKED_IN bookings within the date range using database query
        BigDecimal revenue = bookingRepository.sumRevenueByDateRange(startDate, endDate);
        return revenue != null ? revenue : BigDecimal.ZERO;
    }

    private List<DashboardResponse.TopHomestayDto> getTopHomestays(LocalDateTime startDate, LocalDateTime endDate) {
        // Get homestays with their booking counts and revenue within the date range using database queries
        return homestayRepository.findAll().stream()
                .map(homestay -> {
                    List<com.exe201.project.exe_201_beestay_be.models.Booking> checkedInBookings = 
                        bookingRepository.findCheckedInBookingsByHomestayAndDateRange(homestay.getId(), startDate, endDate);
                    
                    long bookingCount = checkedInBookings.size();
                    BigDecimal revenue = checkedInBookings.stream()
                            .map(booking -> booking.getTotalPrice())
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    
                    DashboardResponse.TopHomestayDto dto = new DashboardResponse.TopHomestayDto();
                    dto.setHomestayName(homestay.getName());
                    dto.setHostName(homestay.getHost() != null ? homestay.getHost().getName() : "Unknown Host");
                    dto.setBookingCount(bookingCount);
                    dto.setRevenue(revenue);
                    dto.setAverageRating(homestay.getAverageRating());
                    dto.setReviewCount(homestay.getReviewCount());
                    
                    DashboardResponse.LocationDto location = new DashboardResponse.LocationDto();
                    location.setAddress(homestay.getAddress());
                    location.setDistrict(homestay.getDistrict());
                    location.setCity(homestay.getCity());
                    location.setProvince(homestay.getProvince());
                    dto.setLocation(location);
                    
                    return dto;
                })
                .filter(dto -> dto.getBookingCount() > 0) // Only include homestays with bookings
                .sorted((a, b) -> Long.compare(b.getBookingCount(), a.getBookingCount())) // Sort by booking count desc
                .limit(5) // Top 5
                .collect(Collectors.toList());
    }
}