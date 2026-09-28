package com.example.loanmanagement.repository;


import com.example.loanmanagement.entity.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanTypeRepository extends JpaRepository<LoanType, Long> {

    Optional<LoanType> findByIdAndDeletedFalse(Long id);

    List<LoanType> findAllByDeletedFalse();

    boolean existsByNameIgnoreCaseAndDeletedFalse(String name);
}

