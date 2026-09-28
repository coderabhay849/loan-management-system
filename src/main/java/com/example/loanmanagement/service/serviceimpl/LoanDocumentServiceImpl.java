package com.example.loanmanagement.service.serviceimpl;

import com.example.loanmanagement.entity.LoanApplication;
import com.example.loanmanagement.entity.LoanDocument;
import com.example.loanmanagement.entity.User;
import com.example.loanmanagement.repository.LoanApplicationRepository;
import com.example.loanmanagement.repository.LoanDocumentRepository;
import com.example.loanmanagement.repository.UserRepository;
import com.example.loanmanagement.service.LoanDocumentService;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoanDocumentServiceImpl implements LoanDocumentService {

    private final LoanDocumentRepository loanDocumentRepository;
    private final LoanApplicationRepository loanApplicationRepository;
    private final UserRepository userRepository;

    private final Path uploadDirectory =
            Paths.get("uploads/loan-documents");

    public LoanDocumentServiceImpl(
            LoanDocumentRepository loanDocumentRepository,
            LoanApplicationRepository loanApplicationRepository,
            UserRepository userRepository) {

        this.loanDocumentRepository = loanDocumentRepository;
        this.loanApplicationRepository = loanApplicationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public LoanDocument uploadDocument(
            Long loanId,
            String documentType,
            MultipartFile file,
            String customerEmail) {

        User customer = userRepository.findByEmail(customerEmail)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        LoanApplication loan =
                loanApplicationRepository.findById(loanId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Loan application not found"));

        if (!loan.getCustomer().getId()
                .equals(customer.getId())) {

            throw new RuntimeException(
                    "You are not authorized to upload documents for this loan");
        }

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File cannot be empty");
        }

        try {

            Files.createDirectories(uploadDirectory);

            String originalFileName = file.getOriginalFilename();

            if (originalFileName == null || originalFileName.isBlank()) {
                throw new RuntimeException("Invalid file name");
            }

            String fileName =
                    System.currentTimeMillis()
                    + "_"
                    + originalFileName;

            Path filePath =
                    uploadDirectory.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING);

            LoanDocument document = new LoanDocument();

            document.setLoanApplication(loan);
            document.setDocumentType(documentType);
            document.setFileName(file.getOriginalFilename());
            document.setFilePath(filePath.toString());
            document.setUploadedAt(LocalDateTime.now());
            document.setDeleted(false);

            return loanDocumentRepository.save(document);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to store document", e);
        }
    }

    @Override
    public List<LoanDocument> getDocuments(
            Long loanId,
            String customerEmail) {

        User customer = userRepository.findByEmail(customerEmail)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        LoanApplication loan =
                loanApplicationRepository.findById(loanId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Loan application not found"));

        if (!loan.getCustomer().getId()
                .equals(customer.getId())) {

            throw new RuntimeException(
                    "You are not authorized to view these documents");
        }

        return loanDocumentRepository
                .findByLoanApplicationIdAndDeletedFalse(loanId);
    }

    @Override
    public void deleteDocument(
            Long documentId,
            String customerEmail) {

        User customer = userRepository.findByEmail(customerEmail)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        LoanDocument document =
                loanDocumentRepository
                        .findByIdAndDeletedFalse(documentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Document not found"));

        if (!document.getLoanApplication()
                .getCustomer().getId()
                .equals(customer.getId())) {

            throw new RuntimeException(
                    "You are not authorized to delete this document");
        }

        document.setDeleted(true);

        loanDocumentRepository.save(document);
    }
}