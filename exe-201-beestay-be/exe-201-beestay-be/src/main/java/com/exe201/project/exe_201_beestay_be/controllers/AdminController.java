package com.exe201.project.exe_201_beestay_be.controllers;


import com.exe201.project.exe_201_beestay_be.services.HostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bee-stay/api/v1")
@RequiredArgsConstructor
public class AdminController {

    private final HostService hostService;

    @GetMapping("/host")
    public ResponseEntity<?> getHost() {
        return ResponseEntity.ok().body(hostService.getHostStayCation());
    }
}
