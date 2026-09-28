package com.example.loanmanagement.service.serviceimpl;

import com.example.loanmanagement.entity.EmiSchedule;
import com.example.loanmanagement.entity.LoanApplication;
import com.example.loanmanagement.entity.LoanStatus;
import com.example.loanmanagement.repository.EmiScheduleRepository;
import com.example.loanmanagement.repository.LoanApplicationRepository;
import com.example.loanmanagement.service.EmiScheduleService;
import com.example.loanmanagement.service.LoanHistoryService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmiScheduleServiceImpl implements EmiScheduleService {

    private final EmiScheduleRepository emiScheduleRepository;
    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanHistoryService loanHistoryService;

    public EmiScheduleServiceImpl(
            EmiScheduleRepository emiScheduleRepository,
            LoanApplicationRepository loanApplicationRepository,
            LoanHistoryService loanHistoryService) {
    	
        this.emiScheduleRepository = emiScheduleRepository;
        this.loanApplicationRepository = loanApplicationRepository;
        this.loanHistoryService = loanHistoryService;
    }

    @Override
    public List<EmiSchedule> generateSchedule(Long loanId) {

        LoanApplication loan = loanApplicationRepository.findById(loanId)
                .orElseThrow(() ->
                        new RuntimeException("Loan application not found"));

        if (loan.getStatus() != LoanStatus.APPROVED) {
            throw new RuntimeException(
                    "EMI schedule can be generated only for approved loan");
        }

        List<EmiSchedule> existing =
                emiScheduleRepository
                        .findByLoanApplicationIdOrderByInstallmentNumberAsc(loanId);

        if (!existing.isEmpty()) {
            return existing;
        }

        double principal = loan.getLoanAmount();
        double annualRate = loan.getInterestRate();
        int months = loan.getTenureMonths();

        double monthlyRate = annualRate / 12 / 100;

        double emi;

        if (monthlyRate == 0) {
            emi = principal / months;
        } else {
            double factor = Math.pow(1 + monthlyRate, months);

            emi = principal * monthlyRate * factor
                    / (factor - 1);
        }

        List<EmiSchedule> schedule = new ArrayList<>();

        double remainingPrincipal = principal;

        LocalDate firstDueDate = LocalDate.now().plusMonths(1);

        for (int i = 1; i <= months; i++) {

            double interest = remainingPrincipal * monthlyRate;

            double principalPart = emi - interest;

            if (i == months) {
                principalPart = remainingPrincipal;
                emi = principalPart + interest;
            }

            remainingPrincipal -= principalPart;

            EmiSchedule installment = new EmiSchedule();

            installment.setLoanApplication(loan);
            installment.setInstallmentNumber(i);
            installment.setDueDate(firstDueDate.plusMonths(i - 1));
            installment.setEmiAmount(round(emi));
            installment.setPrincipalAmount(round(principalPart));
            installment.setInterestAmount(round(interest));
            installment.setPaid(false);

            schedule.add(installment);
        }

        List<EmiSchedule> savedSchedule =
                emiScheduleRepository.saveAll(schedule);

        loanHistoryService.addHistory(
                loanId,
                "EMI_SCHEDULE_GENERATED",
                "EMI schedule generated successfully",
                "LOAN_OFFICER"
        );

        return savedSchedule;
    }

    @Override
    public List<EmiSchedule> getSchedule(Long loanId) {

        return emiScheduleRepository
                .findByLoanApplicationIdOrderByInstallmentNumberAsc(loanId);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}