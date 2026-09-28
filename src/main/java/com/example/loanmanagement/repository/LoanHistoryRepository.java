package com.example.loanmanagement.repository;

import com.example.loanmanagement.entity.LoanHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanHistoryRepository
        extends JpaRepository<LoanHistory, Long> {

    List<LoanHistory> findByLoanApplicationIdOrderByActionDateDesc(
            Long loanApplicationId);
}