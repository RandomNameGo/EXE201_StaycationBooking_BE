package com.exe201.project.exe_201_beestay_be.repositories;

import com.exe201.project.exe_201_beestay_be.models.UserFavoriteHomestay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserFavoriteHomestayRepository extends JpaRepository<UserFavoriteHomestay, Integer> {

  @Query("select uf.homestay.id from UserFavoriteHomestay uf where uf.user.id = :userId")
  List<Integer> findUserFavoriteHomestayIdsByUserId(Integer userId);
}