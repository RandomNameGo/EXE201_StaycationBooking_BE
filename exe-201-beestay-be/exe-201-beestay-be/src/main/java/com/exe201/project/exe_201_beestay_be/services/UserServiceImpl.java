package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.dto.responses.UserDetailResponse;
import com.exe201.project.exe_201_beestay_be.models.User;
import com.exe201.project.exe_201_beestay_be.models.UserFavoriteHomestay;
import com.exe201.project.exe_201_beestay_be.repositories.UserFavoriteHomestayRepository;
import com.exe201.project.exe_201_beestay_be.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final UserFavoriteHomestayRepository userFavoriteHomestayRepository;

    @Override
    public UserDetailResponse getUserDetails(int id) {
        UserDetailResponse userDetailResponse = new UserDetailResponse();
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            userDetailResponse.setId(user.get().getId());
            userDetailResponse.setName(user.get().getName());
            userDetailResponse.setEmail(user.get().getEmail());
            userDetailResponse.setPhone(user.get().getPhone());
            userDetailResponse.setGender(user.get().getGender());
            userDetailResponse.setBirthDate(user.get().getBirthDate());

            UserDetailResponse.AddressResponse addressResponse = new UserDetailResponse.AddressResponse();
            addressResponse.setStreet(user.get().getStreet());
            addressResponse.setCity(user.get().getCity());
            addressResponse.setProvince(user.get().getProvince());
            addressResponse.setDistrict(user.get().getDistrict());
            userDetailResponse.setAddressResponse(addressResponse);

            userDetailResponse.setJoinedDate(user.get().getJoinedDate());
            userDetailResponse.setCurrentBooking(user.get().getCurrentBooking());
            userDetailResponse.setTotalBookingSuccess(user.get().getTotalBookingSuccess());
            userDetailResponse.setFavoriteHomestay(userFavoriteHomestayRepository.findUserFavoriteHomestayIdsByUserId(user.get().getId()));

            userDetailResponse.setReviewCount(user.get().getReviewCount());
            userDetailResponse.setStatus(user.get().getStatus());
            userDetailResponse.setVerified(user.get().getIsVerified());

        }else {
            throw new RuntimeException("User not found");
        }
        return userDetailResponse;
    }
}
