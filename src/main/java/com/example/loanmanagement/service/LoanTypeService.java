package com.example.loanmanagement.service;

import com.example.loanmanagement.dto.LoanTypeRequest;
import com.example.loanmanagement.dto.LoanTypeResponse;

import java.util.List;

public interface LoanTypeService {

    LoanTypeResponse createLoanType(LoanTypeRequest request);

    List<LoanTypeResponse> getAllLoanTypes();

    LoanTypeResponse getLoanTypeById(Long id);

    LoanTypeResponse updateLoanType(Long id, LoanTypeRequest request);

    void deleteLoanType(Long id);
}

