package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.configurations.JwtUtil;
import com.exe201.project.exe_201_beestay_be.dto.requests.VerifyOtpRequest;
import com.exe201.project.exe_201_beestay_be.dto.responses.LoginResponse;
import com.exe201.project.exe_201_beestay_be.exceptions.AccountNotValidException;
import com.exe201.project.exe_201_beestay_be.models.Account;
import com.exe201.project.exe_201_beestay_be.models.Enums.Roles;
import com.exe201.project.exe_201_beestay_be.models.Host;
import com.exe201.project.exe_201_beestay_be.models.User;
import com.exe201.project.exe_201_beestay_be.repositories.AccountRepository;
import com.exe201.project.exe_201_beestay_be.repositories.HostRepository;
import com.exe201.project.exe_201_beestay_be.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    private final HostRepository hostRepository;

    @Override
    public LoginResponse login(String userName, String password) {
        Account accountOptional = accountRepository.findByUserNameAndStatusIsTrue(userName);

        if(accountOptional==null || !accountOptional.getStatus()) {
            throw new AccountNotValidException("Account is not valid");
        }

        if (passwordEncoder.matches(password, accountOptional.getPassword())) {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setAccountId(accountOptional.getId());
            loginResponse.setUserName(accountOptional.getUserName());
            loginResponse.setToken(jwtUtil.generateToken(accountOptional.getUserName(), accountOptional.getRole()));
            return loginResponse;
        }

        return null;
    }

    @Override
    public String register(VerifyOtpRequest register) {
        String password = passwordEncoder.encode(register.getPassword());
        if(register.getUserName() == null || register.getUserName().isEmpty()  || register.getPassword() == null || register.getPassword().isEmpty()
                || register.getRole() == null || register.getRole().isEmpty()) {
            throw new AccountNotValidException("Account is not valid");
        }

        Account account = new Account();
        account.setUserName(register.getUserName());
        account.setPassword(password);
        account.setRole(Roles.valueOf(register.getRole()));
        account.setStatus(true);
        accountRepository.save(account);

        Account tempAccount = accountRepository.findTopByOrderByIdDesc();

        if(Objects.equals(register.getRole(), "USER")){
            User user = new User();
            user.setStatus("active");
            user.setAccount(tempAccount);
            user.setReviewCount(0);
            user.setIsVerified(true);
            user.setCurrentBooking(0);
            user.setTotalBookingSuccess(0);
            user.setEmail(register.getEmail());
            user.setJoinedDate(LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh")));
            userRepository.save(user);
        } else if(Objects.equals(register.getRole(), "HOST")){
            Host host = new Host();
            host.setStatus("active");
            host.setAccount(tempAccount);
            host.setTotalRooms(0);
            host.setAverageRating(0f);
            hostRepository.save(host);
        }

        return "Created Successfully";
    }
}
