package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.configurations.JwtUtil;
import com.exe201.project.exe_201_beestay_be.dto.responses.LoginResponse;
import com.exe201.project.exe_201_beestay_be.exceptions.RefreshTokenBotValidException;
import com.exe201.project.exe_201_beestay_be.models.Account;
import com.exe201.project.exe_201_beestay_be.models.RefreshToken;
import com.exe201.project.exe_201_beestay_be.repositories.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtUtil jwtUtil;

    public String createRefreshToken(Account account) {
        RefreshToken token = new RefreshToken();
        token.setAccount(account);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Date.from(Instant.now().plus(30, ChronoUnit.DAYS)));
        refreshTokenRepository.save(token);
        return token.getToken();
    }

    public LoginResponse createNewToken(String token){
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByRefreshToken(token);
        if(refreshToken.isEmpty()){
            throw new RefreshTokenBotValidException("Refresh token not found");
        }
        if(isTokenExpired(refreshToken.get())){
            throw new RefreshTokenBotValidException("Refresh token is expired");
        }
        Account account = refreshToken.get().getAccount();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccountId(account.getId());
        loginResponse.setUserName(account.getUserName());
        loginResponse.setRole(account.getRole().toString());
        loginResponse.setToken(jwtUtil.generateToken(account.getUserName(), account.getRole()));
        loginResponse.setRefreshToken(this.createRefreshToken(account));
        return loginResponse;
    }

    public boolean isTokenExpired(RefreshToken token) {
        return token.getExpiryDate().before(new Date());
    }


}
