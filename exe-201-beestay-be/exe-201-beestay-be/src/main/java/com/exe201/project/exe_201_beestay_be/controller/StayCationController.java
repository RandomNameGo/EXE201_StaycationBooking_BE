package com.exe201.project.exe_201_beestay_be.controller;

import com.exe201.project.exe_201_beestay_be.dto.requests.StayCationCreateRequest;
import com.exe201.project.exe_201_beestay_be.dto.requests.StayCationUpdateRequest;
import com.exe201.project.exe_201_beestay_be.exceptions.HostNotFoundException;
import com.exe201.project.exe_201_beestay_be.exceptions.StayCationNotFoundException;
import com.exe201.project.exe_201_beestay_be.services.HomestayService;
import com.exe201.project.exe_201_beestay_be.dto.responses.StayCationDetailResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/stay-cation/add/{accountId}")
    public ResponseEntity<?> add(@RequestBody StayCationCreateRequest stayCationCreateRequest, @PathVariable int accountId){
        try {
            return ResponseEntity.ok().body(homestayService.addStayCation(stayCationCreateRequest, accountId));
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("stay-cation/add-img/{id}")
    public ResponseEntity<?> addImg(@PathVariable("id") int id, @RequestParam("image") List<MultipartFile> imageList) {
        try {
            return ResponseEntity.ok().body(homestayService.uploadHomestayImage(imageList, id));
        } catch (StayCationNotFoundException e){
            throw new StayCationNotFoundException(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/stay-cation/{homeStayId}")
    public ResponseEntity<?> update(@PathVariable int homeStayId, @RequestBody StayCationUpdateRequest stayCationUpdateRequest){
        try {
            return ResponseEntity.ok().body(homestayService.updateStayCation(homeStayId, stayCationUpdateRequest));
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/stay-cation/get-by-host/{accountId}")
    public ResponseEntity<?> getByHost(@PathVariable int accountId){
        try {
            return ResponseEntity.ok().body(homestayService.getByHost(accountId));
        } catch (HostNotFoundException ex){
            throw new HostNotFoundException(ex.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
