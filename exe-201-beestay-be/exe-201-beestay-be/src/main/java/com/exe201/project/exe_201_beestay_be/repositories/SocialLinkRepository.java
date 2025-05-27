package com.exe201.project.exe_201_beestay_be.repositories;

import com.exe201.project.exe_201_beestay_be.models.SocialLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SocialLinkRepository extends JpaRepository<SocialLink, Integer> {

    @Query("select s from SocialLink s where s.host.id = :id")
    List<SocialLink> findByHostId(int id);
}