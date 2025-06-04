package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.dto.responses.HostDetailResponse;
import com.exe201.project.exe_201_beestay_be.dto.responses.LocationResponse;
import com.exe201.project.exe_201_beestay_be.models.Host;
import com.exe201.project.exe_201_beestay_be.models.SocialLink;
import com.exe201.project.exe_201_beestay_be.repositories.HomestayRepository;
import com.exe201.project.exe_201_beestay_be.repositories.HostRepository;
import com.exe201.project.exe_201_beestay_be.repositories.SocialLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HostServiceImpl implements HostService{

    private final HostRepository hostRepository;

    private final HomestayRepository homestayRepository;

    private final SocialLinkRepository linkRepository;

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
