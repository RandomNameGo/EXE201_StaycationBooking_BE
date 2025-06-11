package com.exe201.project.exe_201_beestay_be.controller;

import com.exe201.project.exe_201_beestay_be.dto.requests.StayCationCreateRequest;
import com.exe201.project.exe_201_beestay_be.dto.requests.StayCationUpdateRequest;
import com.exe201.project.exe_201_beestay_be.exceptions.StayCationNotFoundException;
import com.exe201.project.exe_201_beestay_be.services.HomestayService;
import com.exe201.project.exe_201_beestay_be.dto.responses.StayCationDetailResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/bee-stay/api/v1")
@RequiredArgsConstructor
public class StayCationController {

    private final HomestayService homestayService;

    @GetMapping("/stay-cation/all")
    public ResponseEntity<List<StayCationDetailResponse>> getAll(){
            return ResponseEntity.ok().body(homestayService.getAllRoomDetails());

    }

    @PostMapping("/stay-cation/add")
    public ResponseEntity<?> add(@RequestBody StayCationCreateRequest stayCationCreateRequest){
        try {
            return ResponseEntity.ok().body(homestayService.addStayCation(stayCationCreateRequest));
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("stay-cation/add-img/{id}")
    public ResponseEntity<?> addImg(@PathVariable("id") int id, @RequestParam("image") List<MultipartFile> imageList) throws IOException {
        try {
            return ResponseEntity.ok().body(homestayService.uploadHomestayImage(imageList, id));
        } catch (StayCationNotFoundException e){
            throw new StayCationNotFoundException(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/stay-cation/{homeStayId}")
    public ResponseEntity<?> update(@PathVariable int homeStayId, @RequestBody StayCationUpdateRequest stayCationUpdateRequest){
        try {
            return ResponseEntity.ok().body(homestayService.updateStayCation(homeStayId, stayCationUpdateRequest));
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
