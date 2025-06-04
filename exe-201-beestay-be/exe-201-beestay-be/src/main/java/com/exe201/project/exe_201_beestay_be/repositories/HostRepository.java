package com.exe201.project.exe_201_beestay_be.repositories;

import com.exe201.project.exe_201_beestay_be.models.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HostRepository extends JpaRepository<Host, Integer> {

    @Query("select h from Host h where h.account.id = :accountId")
    Optional<Host> findByAccountId(int accountId);
}