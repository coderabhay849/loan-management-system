package com.example.loanmanagement.service.serviceimpl;

import com.example.loanmanagement.dto.LoanTypeRequest;
import com.example.loanmanagement.dto.LoanTypeResponse;
import com.example.loanmanagement.entity.LoanType;
import com.example.loanmanagement.repository.LoanTypeRepository;
import com.example.loanmanagement.service.LoanTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanTypeServiceImpl implements LoanTypeService {

    private final LoanTypeRepository loanTypeRepository;

    public LoanTypeServiceImpl(LoanTypeRepository loanTypeRepository) {
        this.loanTypeRepository = loanTypeRepository;
    }

    @Override
    public LoanTypeResponse createLoanType(LoanTypeRequest request) {

        if (loanTypeRepository
                .existsByNameIgnoreCaseAndDeletedFalse(request.getName())) {
            throw new RuntimeException("Loan type already exists");
        }

        LoanType loanType = new LoanType();

        loanType.setName(request.getName());
        loanType.setDescription(request.getDescription());
        loanType.setInterestRate(request.getInterestRate());
        loanType.setMaxAmount(request.getMaxAmount());
        loanType.setMaxTenureMonths(request.getMaxTenureMonths());
        loanType.setActive(true);
        loanType.setDeleted(false);

        LoanType savedLoanType = loanTypeRepository.save(loanType);

        return mapToResponse(savedLoanType);
    }

    @Override
    public List<LoanTypeResponse> getAllLoanTypes() {

        return loanTypeRepository.findAllByDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public LoanTypeResponse getLoanTypeById(Long id) {

        LoanType loanType = loanTypeRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new RuntimeException("Loan type not found"));

        return mapToResponse(loanType);
    }

    @Override
    public LoanTypeResponse updateLoanType(
            Long id,
            LoanTypeRequest request) {

        LoanType loanType = loanTypeRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new RuntimeException("Loan type not found"));

        loanType.setName(request.getName());
        loanType.setDescription(request.getDescription());
        loanType.setInterestRate(request.getInterestRate());
        loanType.setMaxAmount(request.getMaxAmount());
        loanType.setMaxTenureMonths(request.getMaxTenureMonths());

        LoanType updatedLoanType =
                loanTypeRepository.save(loanType);

        return mapToResponse(updatedLoanType);
    }

    @Override
    public void deleteLoanType(Long id) {

        LoanType loanType = loanTypeRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new RuntimeException("Loan type not found"));

        loanType.setDeleted(true);
        loanTypeRepository.save(loanType);
    }

    private LoanTypeResponse mapToResponse(LoanType loanType) {

        return new LoanTypeResponse(
                loanType.getId(),
                loanType.getName(),
                loanType.getDescription(),
                loanType.getInterestRate(),
                loanType.getMaxAmount(),
                loanType.getMaxTenureMonths(),
                loanType.isActive()
        );
    }
}
