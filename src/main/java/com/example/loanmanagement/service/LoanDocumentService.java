package com.example.loanmanagement.service;

import com.example.loanmanagement.entity.LoanDocument;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LoanDocumentService {

    LoanDocument uploadDocument(
            Long loanId,
            String documentType,
            MultipartFile file,
            String customerEmail);

    List<LoanDocument> getDocuments(
            Long loanId,
            String customerEmail);

    void deleteDocument(
            Long documentId,
            String customerEmail);
}