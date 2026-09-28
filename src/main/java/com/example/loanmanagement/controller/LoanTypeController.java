package com.example.loanmanagement.controller;

import com.example.loanmanagement.dto.LoanTypeRequest;
import com.example.loanmanagement.dto.LoanTypeResponse;
import com.example.loanmanagement.service.LoanTypeService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/loan-types")
public class LoanTypeController {

    private final LoanTypeService loanTypeService;

    public LoanTypeController(LoanTypeService loanTypeService) {
        this.loanTypeService = loanTypeService;
    }

    @PostMapping
    public ResponseEntity<LoanTypeResponse> createLoanType(
            @Valid @RequestBody LoanTypeRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(loanTypeService.createLoanType(request));
    }

    @GetMapping
    public ResponseEntity<List<LoanTypeResponse>> getAllLoanTypes() {

        return ResponseEntity.ok(
                loanTypeService.getAllLoanTypes()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanTypeResponse> getLoanTypeById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                loanTypeService.getLoanTypeById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanTypeResponse> updateLoanType(
            @PathVariable Long id,
            @Valid @RequestBody LoanTypeRequest request) {

        return ResponseEntity.ok(
                loanTypeService.updateLoanType(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLoanType(
            @PathVariable Long id) {

        loanTypeService.deleteLoanType(id);

        return ResponseEntity.ok(
                "Loan type deleted successfully"
        );
    }
}
