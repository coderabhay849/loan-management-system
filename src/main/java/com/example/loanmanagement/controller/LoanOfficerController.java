package com.example.loanmanagement.controller;

	import com.example.loanmanagement.dto.LoanApplicationResponse;
	import com.example.loanmanagement.dto.LoanReviewRequest;
	import com.example.loanmanagement.service.LoanApplicationService;

	import jakarta.validation.Valid;

	import org.springframework.http.ResponseEntity;
	import org.springframework.security.core.Authentication;
	import org.springframework.web.bind.annotation.*;
	import com.example.loanmanagement.entity.LoanApplication;
	import com.example.loanmanagement.entity.LoanStatus;
	import com.example.loanmanagement.repository.LoanApplicationRepository;
	import org.springframework.data.domain.Page;
	import org.springframework.data.domain.PageRequest;
	import org.springframework.data.domain.Pageable;

	@RestController
	@RequestMapping("/api/officer/loans")
	public class LoanOfficerController {

	    private final LoanApplicationService loanApplicationService;
	    private final LoanApplicationRepository loanApplicationRepository;
	    
	    
	    public LoanOfficerController(
	            LoanApplicationService loanApplicationService,
	            LoanApplicationRepository loanApplicationRepository) {

	        this.loanApplicationService = loanApplicationService;
	        this.loanApplicationRepository = loanApplicationRepository;
	    }
	    

	    @GetMapping("/{id}")
	    public ResponseEntity<LoanApplicationResponse> getLoan(
	            @PathVariable Long id) {

	        return ResponseEntity.ok(
	                loanApplicationService.getLoanForOfficer(id));
	    }

	    @PutMapping("/{id}/review")
	    public ResponseEntity<LoanApplicationResponse> reviewLoan(
	            @PathVariable Long id,
	            @Valid @RequestBody LoanReviewRequest request,
	            Authentication authentication) {

	        String officerEmail = authentication.getName();

	        return ResponseEntity.ok(
	                loanApplicationService.reviewLoan(
	                        id, request, officerEmail));
	    }
	    
	    @PutMapping("/{id}/close")
	    public ResponseEntity<String> closeLoan(
	            @PathVariable Long id) {

	        loanApplicationService.checkAndCloseLoan(id);

	        return ResponseEntity.ok(
	                "Loan closed successfully");
	    }
	    @GetMapping
	    public ResponseEntity<Page<LoanApplication>> searchLoans(
	            @RequestParam(required = false) LoanStatus status,
	            @RequestParam(required = false) String customerEmail,
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size) {

	        Pageable pageable = PageRequest.of(page, size);

	        Page<LoanApplication> result;

	        if (status != null) {
	            result = loanApplicationRepository
	                    .findByStatus(status, pageable);

	        } else if (customerEmail != null &&
	                   !customerEmail.isBlank()) {

	            result = loanApplicationRepository
	                    .findByCustomerEmailContainingIgnoreCase(
	                            customerEmail, pageable);

	        } else {
	            result = loanApplicationRepository
	                    .findAll(pageable);
	        }

	        return ResponseEntity.ok(result);
	    }
	}

