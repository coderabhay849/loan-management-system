package com.example.loanmanagement.controller;

import com.example.loanmanagement.dto.LoanApplicationRequest;
import com.example.loanmanagement.dto.LoanApplicationResponse;
import com.example.loanmanagement.service.LoanApplicationService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer/loans")
public class LoanApplicationController {

    private final LoanApplicationService loanApplicationService;

    public LoanApplicationController(
            LoanApplicationService loanApplicationService) {
        this.loanApplicationService = loanApplicationService;
    }

    @PostMapping
    public ResponseEntity<LoanApplicationResponse> applyForLoan(
            @Valid @RequestBody LoanApplicationRequest request,
            Authentication authentication) {

        String customerEmail = authentication.getName();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        loanApplicationService.applyForLoan(
                                request,
                                customerEmail
                        )
                );
    }

    @GetMapping
    public ResponseEntity<List<LoanApplicationResponse>> getMyLoans(
            Authentication authentication) {

        String customerEmail = authentication.getName();

        return ResponseEntity.ok(
                loanApplicationService.getMyLoans(customerEmail)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanApplicationResponse> getLoanById(
            @PathVariable Long id,
            Authentication authentication) {

        String customerEmail = authentication.getName();

        return ResponseEntity.ok(
                loanApplicationService.getLoanById(
                        id,
                        customerEmail
                )
        );
    }

}
