package com.exe201.project.exe_201_beestay_be.controller;

import com.exe201.project.exe_201_beestay_be.dto.responses.CreateHostSubscriptionRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.type.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/bee-stay/api/v1")
@RequiredArgsConstructor
public class PaymentController {
    private final PayOS payOS;

    @GetMapping("/payment/success")
    public ResponseEntity<?> paymentSuccess() {
        return ResponseEntity.ok("success");
    }

    @GetMapping("/payment/cancel")
    public ResponseEntity<?> paymentCancel() {
        return ResponseEntity.ok("cancel");
    }

    @PostMapping(path = "/payos_transfer_handler")
    public ObjectNode payosTransferHandler(@RequestBody ObjectNode body)
            throws JsonProcessingException, IllegalArgumentException {

        System.out.println(body.toPrettyString());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        Webhook webhookBody = objectMapper.treeToValue(body, Webhook.class);

        try {
            // Init Response
            response.put("error", 0);
            response.put("message", "Webhook delivered");
            response.set("data", null);

            WebhookData data = payOS.verifyPaymentWebhookData(webhookBody);
            System.out.println(data);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", e.getMessage());
            response.set("data", null);
            return response;
        }
    }

    @PostMapping(path = "/create")
    public ObjectNode createPaymentLink(@RequestBody CreateHostSubscriptionRequest RequestBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            final String productName = RequestBody.getSubscriptionName();
            final String description = RequestBody.getSubscriptionDescription();
            final String returnUrl = "https://beestay-azgcfsfpgbdkbmgv.southeastasia-01.azurewebsites.net/bee-stay/api/v1/payment/success";
            final String cancelUrl = "https://beestay-azgcfsfpgbdkbmgv.southeastasia-01.azurewebsites.net/bee-stay/api/v1/payment/cancel";
            final int price = RequestBody.getPrice().intValue();
            // Gen order code
            String currentTimeString = String.valueOf(new Date().getTime());
            long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));

            ItemData item = ItemData.builder().name(productName).price(price).quantity(1).build();

            PaymentData paymentData = PaymentData.builder().orderCode(orderCode).description(description).amount(price)
                    .item(item).returnUrl(returnUrl).cancelUrl(cancelUrl).build();

            CheckoutResponseData data = payOS.createPaymentLink(paymentData);

            response.put("error", 0);
            response.put("message", "success");
            response.set("data", objectMapper.valueToTree(data));
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", "fail");
            response.set("data", null);
            return response;

        }
    }
}
