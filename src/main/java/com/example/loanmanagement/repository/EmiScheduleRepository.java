package com.example.loanmanagement.repository;

import com.example.loanmanagement.entity.EmiSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmiScheduleRepository extends JpaRepository<EmiSchedule, Long> {

    List<EmiSchedule> findByLoanApplicationIdOrderByInstallmentNumberAsc(
            Long loanApplicationId);
}