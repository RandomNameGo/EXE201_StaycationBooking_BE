package com.exe201.project.exe_201_beestay_be.controllers;


import com.exe201.project.exe_201_beestay_be.dto.DashboardResponse;
import com.exe201.project.exe_201_beestay_be.dto.responses.PaymentSubscriptionResponse;
import com.exe201.project.exe_201_beestay_be.services.DashboardService;
import com.exe201.project.exe_201_beestay_be.services.HostService;
import com.exe201.project.exe_201_beestay_be.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bee-stay/api/v1")
@RequiredArgsConstructor
public class AdminController {

    private final HostService hostService;
    private final DashboardService dashboardService;
    private final PaymentService paymentService;

    @GetMapping("/host")
    public ResponseEntity<?> getHost() {
        return ResponseEntity.ok().body(hostService.getHostStayCation());
    }

    @GetMapping("/admin/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard(
            @RequestParam(required = false) String month,
            @RequestParam(required = false) String year) {
        
        // Default to current month and year if not provided
        if (month == null || year == null) {
            LocalDate now = LocalDate.now();
            month = month != null ? month : String.valueOf(now.getMonthValue());
            year = year != null ? year : String.valueOf(now.getYear());
        }
        
        DashboardResponse dashboard = dashboardService.getDashboardStatistics(month, year);
        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/admin/transaction")
    public ResponseEntity<List<PaymentSubscriptionResponse>> getAllPaymentSubscriptions() {
        try {
            List<PaymentSubscriptionResponse> paymentSubscriptions = paymentService.getAllPaymentSubscriptions();
            return ResponseEntity.ok(paymentSubscriptions);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
