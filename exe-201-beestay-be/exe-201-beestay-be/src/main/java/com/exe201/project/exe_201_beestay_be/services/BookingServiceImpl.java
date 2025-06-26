package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.dto.requests.CreateBookingRequest;
import com.exe201.project.exe_201_beestay_be.dto.responses.BookingResponse;
import com.exe201.project.exe_201_beestay_be.exceptions.HostNotFoundException;
import com.exe201.project.exe_201_beestay_be.exceptions.UserNotFoundException;
import com.exe201.project.exe_201_beestay_be.models.Booking;
import com.exe201.project.exe_201_beestay_be.models.Homestay;
import com.exe201.project.exe_201_beestay_be.models.Host;
import com.exe201.project.exe_201_beestay_be.models.User;
import com.exe201.project.exe_201_beestay_be.repositories.BookingRepository;
import com.exe201.project.exe_201_beestay_be.repositories.HomestayRepository;
import com.exe201.project.exe_201_beestay_be.repositories.HostRepository;
import com.exe201.project.exe_201_beestay_be.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;

    private final HomestayRepository homestayRepository;

    private final HostRepository hostRepository;

    @Override
    public String addBooking(CreateBookingRequest booking) {

        Optional<User> user = userRepository.findByAccountId(booking.getAccountId());

        Optional<Homestay> homestay = homestayRepository.findById(booking.getHomestayId());

        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        if (homestay.isEmpty()) {
            throw new HostNotFoundException("Homestay not found");
        }

        Booking newBooking = new Booking();
        newBooking.setUser(user.get());
        newBooking.setHomestay(homestay.get());
        newBooking.setFullName(booking.getFullName());
        newBooking.setPhoneNumber(booking.getPhoneNumber());
        newBooking.setCheckIn(booking.getCheckIn());
        newBooking.setCheckOut(booking.getCheckOut());
        newBooking.setStatus("BOOKED");
        newBooking.setTotalPrice(booking.getTotalPrice());
        newBooking.setCreatedAt(LocalDateTime.now());
        newBooking.setPaymentMethod(booking.getPaymentMethod());
        bookingRepository.save(newBooking);

        return "Book successfully";
    }

    @Override
    public String cancelBooking(Long bookingId) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isEmpty()) {
            throw new UserNotFoundException("Booking not found");
        }
        booking.get().setStatus("CANCELLED");
        bookingRepository.save(booking.get());
        return "Cancelled Booking successfully";
    }

    @Override
    public List<BookingResponse> viewBookingByUser(int accountId) {

        Optional<User> user = userRepository.findByAccountId(accountId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        List<Booking> bookingList = bookingRepository.findByBookingByUserId(user.get().getId());
        return getBookingResponses(bookingList);
    }

    @Override
    public List<BookingResponse> viewBookingByHost(int accountId) {
        Optional<Host> host = hostRepository.findByAccountId(accountId);
        if (host.isEmpty()) {
            throw new HostNotFoundException("User not found");
        }
        List<Booking> bookingList = bookingRepository.findByBookingByHostId(host.get().getId());
        return getBookingResponses(bookingList);
    }

    private List<BookingResponse> getBookingResponses(List<Booking> bookingList) {
        List<BookingResponse> bookingResponseList = new ArrayList<>();
        for (Booking booking : bookingList) {
            BookingResponse bookingResponse = new BookingResponse();
            bookingResponse.setBookingId(booking.getId());
            bookingResponse.setFullName(booking.getFullName());
            bookingResponse.setPhoneNumber(booking.getPhoneNumber());
            bookingResponse.setCheckIn(booking.getCheckIn());
            bookingResponse.setCheckOut(booking.getCheckOut());
            bookingResponse.setHomestay(booking.getHomestay().getName());
            bookingResponse.setPaymentMethod(booking.getPaymentMethod());
            bookingResponse.setTotalPrice(booking.getTotalPrice());
            bookingResponseList.add(bookingResponse);
        }

        return bookingResponseList;
    }
}
