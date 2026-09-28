package com.example.loanmanagement.service.serviceimpl;

import com.example.loanmanagement.dto.PaymentRequest;
import com.example.loanmanagement.entity.EmiSchedule;
import com.example.loanmanagement.entity.LoanApplication;
import com.example.loanmanagement.entity.Payment;
import com.example.loanmanagement.entity.User;
import com.example.loanmanagement.repository.EmiScheduleRepository;
import com.example.loanmanagement.repository.LoanApplicationRepository;
import com.example.loanmanagement.repository.PaymentRepository;
import com.example.loanmanagement.repository.UserRepository;
import com.example.loanmanagement.service.LoanHistoryService;
import com.example.loanmanagement.service.PaymentService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final EmiScheduleRepository emiScheduleRepository;
    private final LoanApplicationRepository loanApplicationRepository;
    private final UserRepository userRepository;
    private final LoanHistoryService loanHistoryService;

    public PaymentServiceImpl(
            PaymentRepository paymentRepository,
            EmiScheduleRepository emiScheduleRepository,
            LoanApplicationRepository loanApplicationRepository,
            UserRepository userRepository,LoanHistoryService loanHistoryService) {

        this.paymentRepository = paymentRepository;
        this.emiScheduleRepository = emiScheduleRepository;
        this.loanApplicationRepository = loanApplicationRepository;
        this.userRepository = userRepository;
        this.loanHistoryService = loanHistoryService;
    }
@Transactional
    @Override
    public Payment makePayment(
            PaymentRequest request,
            String customerEmail) {

        User customer = userRepository.findByEmail(customerEmail)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        EmiSchedule emi = emiScheduleRepository.findById(request.getEmiId())
                .orElseThrow(() ->
                        new RuntimeException("EMI not found"));

        LoanApplication loan = emi.getLoanApplication();

        if (!loan.getCustomer().getId().equals(customer.getId())) {
            throw new RuntimeException(
                    "You are not authorized to pay this EMI");
        }

        if (emi.isPaid()) {
            throw new RuntimeException("This EMI is already paid");
        }

        if (Double.compare(request.getAmount(), emi.getEmiAmount()) != 0) {
            throw new RuntimeException(
                "Payment amount must be exactly equal to EMI amount"
            );
        }

        Payment payment = new Payment();

        payment.setLoanApplication(loan);
        payment.setEmiSchedule(emi);
        payment.setAmount(request.getAmount());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus("SUCCESS");

        emi.setPaid(true);
        emiScheduleRepository.save(emi);
        
        
        Payment savedPayment =
                paymentRepository.save(payment);

        loanHistoryService.addHistory(
                loan.getId(),
                "PAYMENT_MADE",
                "EMI payment received: ₹" + request.getAmount(),
                customerEmail
        );

        return savedPayment;
        
    }

    @Override
    public List<Payment> getPaymentHistory(
            Long loanId,
            String customerEmail) {

        User customer = userRepository.findByEmail(customerEmail)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        LoanApplication loan = loanApplicationRepository.findById(loanId)
                .orElseThrow(() ->
                        new RuntimeException("Loan application not found"));

        if (!loan.getCustomer().getId().equals(customer.getId())) {
            throw new RuntimeException(
                    "You are not authorized to view this payment history");
        }

        return paymentRepository
                .findByLoanApplicationIdOrderByPaymentDateDesc(loanId);
    }
}
