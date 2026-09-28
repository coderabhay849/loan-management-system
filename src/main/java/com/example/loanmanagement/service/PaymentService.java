package com.example.loanmanagement.service;

import com.example.loanmanagement.dto.PaymentRequest;
import com.example.loanmanagement.entity.Payment;

import java.util.List;

public interface PaymentService {

    Payment makePayment(PaymentRequest request, String customerEmail);

    List<Payment> getPaymentHistory(
            Long loanId,
            String customerEmail);
}