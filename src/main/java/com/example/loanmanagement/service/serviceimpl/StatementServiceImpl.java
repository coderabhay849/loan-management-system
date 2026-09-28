package com.example.loanmanagement.service.serviceimpl;

import com.example.loanmanagement.entity.LoanApplication;
import com.example.loanmanagement.entity.LoanStatus;
import com.example.loanmanagement.entity.Payment;
import com.example.loanmanagement.repository.LoanApplicationRepository;
import com.example.loanmanagement.repository.PaymentRepository;
import com.example.loanmanagement.service.StatementService;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class StatementServiceImpl implements StatementService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final PaymentRepository paymentRepository;

    public StatementServiceImpl(
            LoanApplicationRepository loanApplicationRepository,
            PaymentRepository paymentRepository) {

        this.loanApplicationRepository = loanApplicationRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Resource generateStatement(
            Long loanId,
            String customerEmail) {

        LoanApplication loan =
                loanApplicationRepository.findById(loanId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Loan application not found"));

        if (!loan.getCustomer().getEmail()
                .equals(customerEmail)) {

            throw new RuntimeException(
                    "You are not authorized to access this statement");
        }

        List<Payment> payments =
                paymentRepository
                        .findByLoanApplicationIdOrderByPaymentDateDesc(
                                loanId);

        StringBuilder statement = new StringBuilder();

        statement.append("LOAN STATEMENT\n");
        statement.append("==============================\n");
        statement.append("Loan ID: ")
                .append(loan.getId()).append("\n");
        statement.append("Customer: ")
                .append(loan.getCustomer().getEmail()).append("\n");
        statement.append("Loan Type: ")
                .append(loan.getLoanType().getName()).append("\n");
        statement.append("Loan Amount: ₹")
                .append(loan.getLoanAmount()).append("\n");
        statement.append("Interest Rate: ")
                .append(loan.getInterestRate()).append("%\n");
        statement.append("Tenure: ")
                .append(loan.getTenureMonths())
                .append(" months\n");
        statement.append("Status: ")
                .append(loan.getStatus()).append("\n\n");

        statement.append("PAYMENT HISTORY\n");
        statement.append("==============================\n");

        if (payments.isEmpty()) {
            statement.append("No payments found.\n");
        } else {
            for (Payment payment : payments) {
                statement.append("Payment ID: ")
                        .append(payment.getId()).append("\n");
                statement.append("Amount: ₹")
                        .append(payment.getAmount()).append("\n");
                statement.append("Date: ")
                        .append(payment.getPaymentDate()).append("\n");
                statement.append("Method: ")
                        .append(payment.getPaymentMethod()).append("\n");
                statement.append("Status: ")
                        .append(payment.getStatus()).append("\n");
                statement.append("------------------------------\n");
            }
        }

        byte[] data = statement.toString()
                .getBytes(StandardCharsets.UTF_8);

        return new ByteArrayResource(data);
    }
}