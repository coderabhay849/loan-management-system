package com.example.loanmanagement.service;

import com.example.loanmanagement.entity.LoanHistory;

import java.util.List;

public interface LoanHistoryService {

    void addHistory(
            Long loanId,
            String action,
            String remarks,
            String performedBy);

    List<LoanHistory> getLoanHistory(Long loanId);
}