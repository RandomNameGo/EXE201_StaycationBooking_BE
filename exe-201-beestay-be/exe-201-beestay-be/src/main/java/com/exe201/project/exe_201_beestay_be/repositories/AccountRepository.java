package com.exe201.project.exe_201_beestay_be.repositories;

import com.exe201.project.exe_201_beestay_be.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUserName(String username);

    @Query("select a from Account a where a.userName = :userName and a.status is true ")
    Account findByUserNameAndStatusIsTrue(String userName);

    Account findTopByOrderByIdDesc();
}