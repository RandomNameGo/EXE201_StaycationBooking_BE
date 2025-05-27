package com.exe201.project.exe_201_beestay_be.dto.responses;

import lombok.Data;

@Data
public class PoliciesResponse {
    private boolean isRefundable;
    private boolean allowPet;
    private boolean allowSmoking;
}
