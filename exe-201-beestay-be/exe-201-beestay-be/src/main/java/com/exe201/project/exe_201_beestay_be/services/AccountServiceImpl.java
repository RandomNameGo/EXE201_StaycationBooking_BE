package com.exe201.project.exe_201_beestay_be.services;

import com.exe201.project.exe_201_beestay_be.configurations.JwtUtil;
import com.exe201.project.exe_201_beestay_be.dto.requests.RegisterAccountRequest;
import com.exe201.project.exe_201_beestay_be.dto.requests.VerifyOtpRequest;
import com.exe201.project.exe_201_beestay_be.exceptions.AccountNotValidException;
import com.exe201.project.exe_201_beestay_be.models.Account;
import com.exe201.project.exe_201_beestay_be.models.Enums.Roles;
import com.exe201.project.exe_201_beestay_be.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Override
    public String login(String userName, String password) {
        Account accountOptional = accountRepository.findByUserName(userName);

        if(accountOptional==null || !accountOptional.getStatus()) {
            throw new AccountNotValidException("Account is not valid");
        }

        if (passwordEncoder.matches(password, accountOptional.getPassword())) {
            return jwtUtil.generateToken(accountOptional.getUserName(), accountOptional.getRole());
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

        return "Created Successfully";
    }
}
