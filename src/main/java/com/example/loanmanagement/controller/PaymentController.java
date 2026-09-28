package com.example.loanmanagement.controller;

import com.example.loanmanagement.dto.PaymentRequest;
import com.example.loanmanagement.entity.Payment;
import com.example.loanmanagement.service.PaymentService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Payment> makePayment(
            @Valid @RequestBody PaymentRequest request,
            Authentication authentication) {

        String customerEmail = authentication.getName();

        return ResponseEntity.ok(
                paymentService.makePayment(
                        request,
                        customerEmail));
    }

    @GetMapping("/loan/{loanId}")
    public ResponseEntity<List<Payment>> getPaymentHistory(
            @PathVariable Long loanId,
            Authentication authentication) {

        String customerEmail = authentication.getName();

        return ResponseEntity.ok(
                paymentService.getPaymentHistory(
                        loanId,
                        customerEmail));
    }
}