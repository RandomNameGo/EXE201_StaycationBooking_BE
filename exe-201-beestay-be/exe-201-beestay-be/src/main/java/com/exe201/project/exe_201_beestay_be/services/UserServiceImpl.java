package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.dto.requests.UpdateUserDetailRequest;
import com.exe201.project.exe_201_beestay_be.dto.responses.UserDetailResponse;
import com.exe201.project.exe_201_beestay_be.exceptions.UserNotFoundException;
import com.exe201.project.exe_201_beestay_be.models.User;
import com.exe201.project.exe_201_beestay_be.repositories.UserFavoriteHomestayRepository;
import com.exe201.project.exe_201_beestay_be.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


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
        return getUserDetailResponse(userDetailResponse, user);
    }

    @Override
    public UserDetailResponse getUserDetailsByAccount(int accountId) {
        UserDetailResponse userDetailResponse = new UserDetailResponse();
        Optional<User> user = userRepository.findByAccountId(accountId);
        return getUserDetailResponse(userDetailResponse, user);
    }

    @Override
    public String updateUserDetails(UpdateUserDetailRequest request) {
        Optional<User> user = userRepository.findById(request.getId());
        if (user.isPresent()) {
            User userDetails = user.get();

            if (request.getName() != null && !request.getName().trim().isEmpty()) {
                userDetails.setName(request.getName());
            }

            if (request.getPhone() != null && !request.getPhone().trim().isEmpty()) {
                userDetails.setPhone(request.getPhone());
            }
            if (request.getGender() != null) {
                userDetails.setGender(request.getGender());
            }
            if (request.getBirthDate() != null) {
                userDetails.setBirthDate(request.getBirthDate());
            }

            if (request.getAddressResponse() != null) {
                if (request.getAddressResponse().getCity() != null && !request.getAddressResponse().getCity().trim().isEmpty()) {
                    userDetails.setCity(request.getAddressResponse().getCity());
                }
                if (request.getAddressResponse().getDistrict() != null && !request.getAddressResponse().getDistrict().trim().isEmpty()) {
                    userDetails.setDistrict(request.getAddressResponse().getDistrict());
                }
                if (request.getAddressResponse().getProvince() != null && !request.getAddressResponse().getProvince().trim().isEmpty()) {
                    userDetails.setProvince(request.getAddressResponse().getProvince());
                }
                if (request.getAddressResponse().getStreet() != null && !request.getAddressResponse().getStreet().trim().isEmpty()) {
                    userDetails.setStreet(request.getAddressResponse().getStreet());
                }
            }

            userRepository.save(userDetails);
        }
        else {
            throw new UserNotFoundException("User not found");
        }
        return "Updated user details successfully";
    }

    private UserDetailResponse getUserDetailResponse(UserDetailResponse userDetailResponse, Optional<User> user) {
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
            throw new UserNotFoundException("User not found");
        }
        return userDetailResponse;
    }
}
