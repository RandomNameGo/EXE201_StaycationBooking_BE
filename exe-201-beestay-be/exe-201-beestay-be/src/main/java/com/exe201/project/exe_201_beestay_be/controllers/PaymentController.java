package com.exe201.project.exe_201_beestay_be.controllers;

import com.exe201.project.exe_201_beestay_be.dto.requests.CreateHostSubscriptionRequest;
import com.exe201.project.exe_201_beestay_be.services.HostSubscriptionService;
import com.exe201.project.exe_201_beestay_be.services.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.type.*;

import java.util.Date;

@RestController
@RequestMapping("/bee-stay/api/v1")
@RequiredArgsConstructor
public class PaymentController {
    private final PayOS payOS;

    private final PaymentService paymentService;

    private final HostSubscriptionService hostSubscriptionService;

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

        long orderCode = webhookBody.getData().getOrderCode();

        try {
            response.put("error", 0);
            response.put("message", "Webhook delivered");
            response.set("data", null);

            WebhookData data = payOS.verifyPaymentWebhookData(webhookBody);
            paymentService.updatePayment(orderCode);
            paymentService.addHostSubscription(orderCode);
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

    @PostMapping("/create-payment-link")
    public ResponseEntity<?> createPaymentLink(@RequestBody CreateHostSubscriptionRequest requestBody) {
        try {
            final String productName = requestBody.getSubscriptionName();
            final String description = requestBody.getSubscriptionDescription();
            final String returnUrl = (requestBody.getReturnUrl() == null || requestBody.getReturnUrl().isEmpty())
                    ? "https://beestay-azgcfsfpgbdkbmgv.southeastasia-01.azurewebsites.net/bee-stay/api/v1/payment/success"
                    : requestBody.getReturnUrl();

            final String cancelUrl = (requestBody.getCancelUrl() == null || requestBody.getCancelUrl().isEmpty())
                    ? "https://beestay-azgcfsfpgbdkbmgv.southeastasia-01.azurewebsites.net/bee-stay/api/v1/payment/cancel"
                    : requestBody.getCancelUrl();
            final int price = requestBody.getPrice().intValue();
            String currentTimeString = String.valueOf(new Date().getTime());
            long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));

            if(hostSubscriptionService.checkHostSubscription(requestBody.getAccountId(), requestBody.getSubscriptionId())){
                hostSubscriptionService.deleteHostSubscription(requestBody.getAccountId(), requestBody.getSubscriptionId());
            }

            ItemData item = ItemData.builder()
                    .name(productName)
                    .price(price)
                    .quantity(1)
                    .build();

            PaymentData paymentData = PaymentData.builder()
                    .orderCode(orderCode)
                    .description(description)
                    .amount(price)
                    .item(item)
                    .returnUrl(returnUrl)
                    .cancelUrl(cancelUrl)
                    .build();

            CheckoutResponseData data = payOS.createPaymentLink(paymentData);

            paymentService.createPayment(requestBody, orderCode);

            String checkoutUrl = data.getCheckoutUrl();
            return ResponseEntity.ok(checkoutUrl);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
