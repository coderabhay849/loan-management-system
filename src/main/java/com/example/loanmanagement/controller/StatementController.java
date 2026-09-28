package com.example.loanmanagement.controller;

import com.example.loanmanagement.service.StatementService;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer/loans")
public class StatementController {

    private final StatementService statementService;

    public StatementController(StatementService statementService) {
        this.statementService = statementService;
    }

    @GetMapping("/{loanId}/statement")
    public ResponseEntity<Resource> downloadStatement(
            @PathVariable Long loanId,
            Authentication authentication) {

        String customerEmail = authentication.getName();

        Resource resource =
                statementService.generateStatement(
                        loanId, customerEmail);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=loan-statement-" + loanId + ".txt")
                .contentType(MediaType.TEXT_PLAIN)
                .body(resource);
    }
}