package com.example.loanmanagement.service;

	import com.example.loanmanagement.dto.LoanApplicationRequest;
	import com.example.loanmanagement.dto.LoanApplicationResponse;
import com.example.loanmanagement.dto.LoanReviewRequest;

import java.util.List;

	public interface LoanApplicationService {

	    LoanApplicationResponse applyForLoan(
	            LoanApplicationRequest request,
	            String customerEmail);

	    List<LoanApplicationResponse> getMyLoans(
	            String customerEmail);

	    LoanApplicationResponse getLoanById(
	            Long id,
	            String customerEmail);
	
	    LoanApplicationResponse reviewLoan(
	            Long loanId,
	            LoanReviewRequest request,
	            String officerEmail);

	    LoanApplicationResponse getLoanForOfficer(Long loanId);
	    
	    void checkAndCloseLoan(Long loanId);
	
	
	
	}

