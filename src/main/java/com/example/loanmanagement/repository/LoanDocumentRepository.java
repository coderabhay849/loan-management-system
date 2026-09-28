package com.example.loanmanagement.repository;

import com.example.loanmanagement.entity.LoanDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanDocumentRepository
        extends JpaRepository<LoanDocument, Long> {

    List<LoanDocument> findByLoanApplicationIdAndDeletedFalse(
            Long loanApplicationId);

    Optional<LoanDocument> findByIdAndDeletedFalse(Long id);


}