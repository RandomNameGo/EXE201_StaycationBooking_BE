package com.exe201.project.exe_201_beestay_be.controller;

import com.exe201.project.exe_201_beestay_be.dto.responses.ApiResponse;
import com.exe201.project.exe_201_beestay_be.services.HomestayService;
import com.exe201.project.exe_201_beestay_be.dto.responses.StayCationDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bees-tay/api/v1")
@RequiredArgsConstructor
public class StayCationController {

    private final HomestayService homestayService;

    @GetMapping("/room/all")
    public ResponseEntity<List<StayCationDetailResponse>> getAll(){
        try{
            return ResponseEntity.ok().body(homestayService.getAllRoomDetails());
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
