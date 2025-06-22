package com.exe201.project.exe_201_beestay_be.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.payos.PayOS;

@Configuration
public class PayOSConfig {
    private String clientId = "591f46c5-597f-47fd-9c9f-e9d379da144b";

    private String apiKey = "35568c9e-a606-4ea1-b96f-cf0ae7175194";

    private String checksumKey = "177039d6d2edc70e8a93ac1cb22cbf5af6f65e9bf98d048adde4b634c864ddf7";

    @Bean
    public PayOS payOS() {
        return new PayOS(clientId, apiKey, checksumKey);
    }
}
