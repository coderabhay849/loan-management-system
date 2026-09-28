package com.example.loanmanagement.service.serviceimpl;

import com.example.loanmanagement.dto.LoanReportResponse;
import com.example.loanmanagement.entity.LoanStatus;
import com.example.loanmanagement.repository.LoanApplicationRepository;
import com.example.loanmanagement.service.LoanReportService;
import org.springframework.stereotype.Service;

@Service
public class LoanReportServiceImpl implements LoanReportService {

    private final LoanApplicationRepository loanApplicationRepository;

    public LoanReportServiceImpl(
            LoanApplicationRepository loanApplicationRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
    }

    @Override
    public LoanReportResponse getLoanReport() {

        long totalLoans = loanApplicationRepository.count();

        long pendingLoans =
                loanApplicationRepository.countByStatus(LoanStatus.PENDING);

        long underReviewLoans =
                loanApplicationRepository.countByStatus(LoanStatus.UNDER_REVIEW);

        long approvedLoans =
                loanApplicationRepository.countByStatus(LoanStatus.APPROVED);

        long rejectedLoans =
                loanApplicationRepository.countByStatus(LoanStatus.REJECTED);

        long activeLoans =
                loanApplicationRepository.countByStatus(LoanStatus.ACTIVE);

        long closedLoans =
                loanApplicationRepository.countByStatus(LoanStatus.CLOSED);

        return new LoanReportResponse(
                totalLoans,
                pendingLoans,
                underReviewLoans,
                approvedLoans,
                rejectedLoans,
                activeLoans,
                closedLoans
        );
    }
}