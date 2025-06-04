package com.exe201.project.exe_201_beestay_be.controller;

import com.exe201.project.exe_201_beestay_be.dto.responses.HostDetailResponse;
import com.exe201.project.exe_201_beestay_be.services.HostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bees-tay/api/v1")
@RequiredArgsConstructor
public class HostController {
    private final HostService hostService;

    @GetMapping("/host/{id}")
    public ResponseEntity<HostDetailResponse> findById(@PathVariable int id) {
        try{
            return ResponseEntity.ok().body(hostService.getHostDetail(id));
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/host/by-account/{accountId}")
    public ResponseEntity<HostDetailResponse> getByAccountId(@PathVariable int accountId) {
        try{
            return ResponseEntity.ok().body(hostService.getHostDetailByAccountId(accountId));
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
