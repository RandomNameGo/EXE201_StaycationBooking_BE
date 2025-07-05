package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.configurations.JwtUtil;
import com.exe201.project.exe_201_beestay_be.dto.requests.UpdateHostDetailRequest;
import com.exe201.project.exe_201_beestay_be.dto.responses.BookingResponse;
import com.exe201.project.exe_201_beestay_be.dto.responses.HostDetailResponse;
import com.exe201.project.exe_201_beestay_be.dto.responses.HostStayCationResponse;
import com.exe201.project.exe_201_beestay_be.dto.responses.LocationResponse;
import com.exe201.project.exe_201_beestay_be.exceptions.HostNotFoundException;
import com.exe201.project.exe_201_beestay_be.exceptions.UnauthorizedException;
import com.exe201.project.exe_201_beestay_be.models.Booking;
import com.exe201.project.exe_201_beestay_be.models.Enums.Roles;
import com.exe201.project.exe_201_beestay_be.models.Homestay;
import com.exe201.project.exe_201_beestay_be.models.Host;
import com.exe201.project.exe_201_beestay_be.models.SocialLink;
import com.exe201.project.exe_201_beestay_be.repositories.BookingRepository;
import com.exe201.project.exe_201_beestay_be.repositories.HomestayRepository;
import com.exe201.project.exe_201_beestay_be.repositories.HostRepository;
import com.exe201.project.exe_201_beestay_be.repositories.SocialLinkRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HostServiceImpl implements HostService{

    private final HostRepository hostRepository;

    private final HomestayRepository homestayRepository;

    private final SocialLinkRepository linkRepository;

    private final CloudinaryService cloudinaryService;

    private final BookingRepository bookingRepository;

    private final HttpServletRequest request;

    private final JwtUtil jwtUtil;

    @Override
    public HostDetailResponse getHostDetail(int id) {
        HostDetailResponse hostDetailResponse = new HostDetailResponse();
        Optional<Host> host = hostRepository.findById(id);
        return getHostDetailResponse(hostDetailResponse, host);
    }

    @Override
    public HostDetailResponse getHostDetailByAccountId(int accountId) {
        HostDetailResponse hostDetailResponse = new HostDetailResponse();
        Optional<Host> host = hostRepository.findByAccountId(accountId);
        return getHostDetailResponse(hostDetailResponse, host);
    }

    @Override
    public String updateHostDetail(UpdateHostDetailRequest request, int accountId) {
        Optional<Host> host = hostRepository.findByAccountId(accountId);
        if (host.isPresent()) {
            Host hostDetail = host.get();

            if (request.getName() != null && !request.getName().isEmpty()) {
                hostDetail.setName(request.getName());
            }

            if (request.getPhone() != null && !request.getPhone().isEmpty()) {
                hostDetail.setPhone(request.getPhone());
            }

            if (request.getLocation() != null) {
                if (request.getLocation().getDistrict() != null && !request.getLocation().getDistrict().isEmpty()) {
                    hostDetail.setDistrict(request.getLocation().getDistrict());
                }

                if (request.getLocation().getCity() != null && !request.getLocation().getCity().isEmpty()) {
                    hostDetail.setCity(request.getLocation().getCity());
                }

                if (request.getLocation().getProvince() != null && !request.getLocation().getProvince().isEmpty()) {
                    hostDetail.setProvince(request.getLocation().getProvince());
                }

                if (request.getLocation().getAddress() != null && !request.getLocation().getAddress().isEmpty()) {
                    hostDetail.setAddress(request.getLocation().getAddress());
                }
            }

            if (request.getBio() != null && !request.getBio().isEmpty()) {
                hostDetail.setBio(request.getBio());
            }

            if (request.getSocialLinks() != null && !request.getSocialLinks().isEmpty()) {
                for (UpdateHostDetailRequest.SocialLinksRequest linksRequest : request.getSocialLinks()) {
                    if (linksRequest.getPlatform() != null && !linksRequest.getPlatform().isEmpty()
                            && linksRequest.getUrl() != null && !linksRequest.getUrl().isEmpty()) {
                        SocialLink socialLink = new SocialLink();
                        socialLink.setHost(hostDetail);
                        socialLink.setSocialName(linksRequest.getPlatform());
                        socialLink.setUrl(linksRequest.getUrl());
                        linkRepository.save(socialLink);
                    }
                }
            }

            hostRepository.save(hostDetail);
            return "Host updated successfully";
        } else {
            throw new HostNotFoundException("Host not found");
        }
    }

    @Override
    public String updateAvatar(MultipartFile file, int accountId) throws IOException {
        Optional<Host> host = hostRepository.findByAccountId(accountId);
        if (host.isPresent()) {
            Host hostDetails = host.get();
            hostDetails.setAvatar(cloudinaryService.uploadFile(file));
            hostRepository.save(hostDetails);
            return "Avatar updated successfully";
        } else {
            throw new HostNotFoundException("Host not found");
        }
    }

    @Override
    public List<HostStayCationResponse> getHostStayCation() {

        String token = jwtUtil.getCurrentToken(request);

        if(!jwtUtil.extractRole(token).equals(Roles.ADMIN)) {
            throw new UnauthorizedException("Unauthorized access");
        }

        List<Host> hostList = hostRepository.findAll();
        List<HostStayCationResponse> hostStayCationResponseList = new ArrayList<>();
        for (Host host : hostList) {
            HostStayCationResponse hostStayCationResponse = new HostStayCationResponse();
            hostStayCationResponse.setId(host.getId());
            hostStayCationResponse.setName(host.getName());
            hostStayCationResponse.setEmail(host.getEmail());
            hostStayCationResponse.setPhone(host.getPhone());
            long bookingCount = bookingRepository.countByHost(host.getId());
            hostStayCationResponse.setTotalBooking(bookingCount);
            List<Homestay> homestayList = homestayRepository.findHomestayByHostId(host.getId());
            List<HostStayCationResponse.StayCationResponse> stayCationResponseList = new ArrayList<>();
            for (Homestay homestay : homestayList) {
                HostStayCationResponse.StayCationResponse stayCationResponse = new HostStayCationResponse.StayCationResponse();
                stayCationResponse.setId(homestay.getId());
                stayCationResponse.setName(homestay.getName());
                LocationResponse locationResponse = new LocationResponse();
                locationResponse.setProvince(homestay.getProvince());
                locationResponse.setCity(homestay.getCity());
                locationResponse.setAddress(homestay.getAddress());
                locationResponse.setDistrict(homestay.getDistrict());
                stayCationResponse.setLocation(locationResponse);
                List<Booking> bookingList = bookingRepository.findBookingByHomestayId(homestay.getId());
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
                stayCationResponse.setBooking(bookingResponseList);
                stayCationResponseList.add(stayCationResponse);
            }
            hostStayCationResponse.setResponse(stayCationResponseList);
            hostStayCationResponseList.add(hostStayCationResponse);
        }
        return hostStayCationResponseList;
    }

    private HostDetailResponse getHostDetailResponse(HostDetailResponse hostDetailResponse, Optional<Host> host) {
        if (host.isPresent()) {

            hostDetailResponse.setId(host.get().getId());
            hostDetailResponse.setName(host.get().getName());
            hostDetailResponse.setPhone(host.get().getPhone());
            hostDetailResponse.setEmail(host.get().getEmail());
            hostDetailResponse.setAvatar(host.get().getAvatar());

            //Location
            LocationResponse locationResponse = new LocationResponse();
            locationResponse.setAddress(host.get().getAddress());
            locationResponse.setCity(host.get().getCity());
            locationResponse.setDistrict(host.get().getDistrict());
            locationResponse.setProvince(host.get().getProvince());
            hostDetailResponse.setLocation(locationResponse);

            hostDetailResponse.setAverageHomestayRating(host.get().getAverageRating());

            //Homestays
            List<Integer> homestays = homestayRepository.findByHostId(host.get().getId());
            hostDetailResponse.setHomeStay(homestays);

            hostDetailResponse.setSuperHost(host.get().getIsSuperHost());
            hostDetailResponse.setJoinedDate(host.get().getJoinedDate());

            //Social link
            List<HostDetailResponse.SocialLinksResponse> socialLinks = new ArrayList<>();
            List<SocialLink> links = linkRepository.findByHostId(host.get().getId());
            for (SocialLink link : links) {
                HostDetailResponse.SocialLinksResponse socialLinksResponse = new HostDetailResponse.SocialLinksResponse();
                socialLinksResponse.setPlatform(link.getSocialName());
                socialLinksResponse.setUrl(link.getUrl());
                socialLinks.add(socialLinksResponse);
            }
            hostDetailResponse.setSocialLinks(socialLinks);
            hostDetailResponse.setStatus(host.get().getStatus());
            hostDetailResponse.setBio(host.get().getBio());
        } else {
            throw new RuntimeException("Host not found");
        }
        return hostDetailResponse;
    }
}
