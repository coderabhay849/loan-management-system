package com.example.loanmanagement.controller;

import com.example.loanmanagement.entity.LoanHistory;
import com.example.loanmanagement.service.LoanHistoryService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/officer/loans")
public class LoanHistoryController {

    private final LoanHistoryService loanHistoryService;

    public LoanHistoryController(LoanHistoryService loanHistoryService) {
        this.loanHistoryService = loanHistoryService;
    }

    @GetMapping("/{loanId}/history")
    public ResponseEntity<List<LoanHistory>> getLoanHistory(
            @PathVariable Long loanId) {

        return ResponseEntity.ok(
                loanHistoryService.getLoanHistory(loanId));
    }
}