package com.example.loanmanagement.repository;

import com.example.loanmanagement.entity.LoanApplication;
import com.example.loanmanagement.entity.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LoanApplicationRepository
        extends JpaRepository<LoanApplication, Long> {

    List<LoanApplication> findByCustomerId(Long customerId);

    List<LoanApplication> findByStatus(LoanStatus status);
    
    long countByStatus(LoanStatus status);
    
    Page<LoanApplication> findByStatus(
            LoanStatus status,
            Pageable pageable);

    Page<LoanApplication> findByCustomerEmailContainingIgnoreCase(
            String email,
            Pageable pageable);
}
	
	

