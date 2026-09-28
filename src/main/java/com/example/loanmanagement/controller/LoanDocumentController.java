package com.example.loanmanagement.controller;

import com.example.loanmanagement.entity.LoanDocument;
import com.example.loanmanagement.service.LoanDocumentService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/customer/loans")
public class LoanDocumentController {

    private final LoanDocumentService loanDocumentService;

    public LoanDocumentController(
            LoanDocumentService loanDocumentService) {
        this.loanDocumentService = loanDocumentService;
    }

    @PostMapping("/{loanId}/documents")
    public ResponseEntity<LoanDocument> uploadDocument(
            @PathVariable Long loanId,
            @RequestParam String documentType,
            @RequestParam MultipartFile file,
            Authentication authentication) {

        String customerEmail = authentication.getName();

        return ResponseEntity.ok(
                loanDocumentService.uploadDocument(
                        loanId,
                        documentType,
                        file,
                        customerEmail));
    }

    @GetMapping("/{loanId}/documents")
    public ResponseEntity<List<LoanDocument>> getDocuments(
            @PathVariable Long loanId,
            Authentication authentication) {

        String customerEmail = authentication.getName();

        return ResponseEntity.ok(
                loanDocumentService.getDocuments(
                        loanId,
                        customerEmail));
    }

    @DeleteMapping("/documents/{documentId}")
    public ResponseEntity<String> deleteDocument(
            @PathVariable Long documentId,
            Authentication authentication) {

        String customerEmail = authentication.getName();

        loanDocumentService.deleteDocument(
                documentId,
                customerEmail);

        return ResponseEntity.ok(
                "Document deleted successfully");
    }
}