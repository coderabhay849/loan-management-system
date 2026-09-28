package com.example.loanmanagement.repository;

import com.example.loanmanagement.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByLoanApplicationIdOrderByPaymentDateDesc(
            Long loanApplicationId);
}