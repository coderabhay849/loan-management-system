package com.example.loanmanagement.controller;

import com.example.loanmanagement.dto.LoanReportResponse;
import com.example.loanmanagement.service.LoanReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/reports")
public class AdminReportController {

    private final LoanReportService loanReportService;

    public AdminReportController(LoanReportService loanReportService) {
        this.loanReportService = loanReportService;
    }

    @GetMapping("/loans")
    public ResponseEntity<LoanReportResponse> getLoanReport() {
        return ResponseEntity.ok(
                loanReportService.getLoanReport()
        );
    }
}