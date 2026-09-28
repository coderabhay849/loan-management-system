package com.example.loanmanagement.service.serviceimpl;

import com.example.loanmanagement.entity.LoanApplication;
import com.example.loanmanagement.entity.LoanHistory;
import com.example.loanmanagement.repository.LoanApplicationRepository;
import com.example.loanmanagement.repository.LoanHistoryRepository;
import com.example.loanmanagement.service.LoanHistoryService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoanHistoryServiceImpl implements LoanHistoryService {

    private final LoanHistoryRepository loanHistoryRepository;
    private final LoanApplicationRepository loanApplicationRepository;

    public LoanHistoryServiceImpl(
            LoanHistoryRepository loanHistoryRepository,
            LoanApplicationRepository loanApplicationRepository) {

        this.loanHistoryRepository = loanHistoryRepository;
        this.loanApplicationRepository = loanApplicationRepository;
    }

    @Override
    public void addHistory(
            Long loanId,
            String action,
            String remarks,
            String performedBy) {

        LoanApplication loan = loanApplicationRepository.findById(loanId)
                .orElseThrow(() ->
                        new RuntimeException("Loan application not found"));

        LoanHistory history = new LoanHistory();

        history.setLoanApplication(loan);
        history.setAction(action);
        history.setRemarks(remarks);
        history.setPerformedBy(performedBy);
        history.setActionDate(LocalDateTime.now());

        loanHistoryRepository.save(history);
    }

    @Override
    public List<LoanHistory> getLoanHistory(Long loanId) {

        loanApplicationRepository.findById(loanId)
                .orElseThrow(() ->
                        new RuntimeException("Loan application not found"));

        return loanHistoryRepository
                .findByLoanApplicationIdOrderByActionDateDesc(loanId);
    }
}