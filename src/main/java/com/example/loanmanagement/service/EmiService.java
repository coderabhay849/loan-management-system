package com.example.loanmanagement.service;

import com.example.loanmanagement.dto.EmiResponse;

public interface EmiService {

    EmiResponse calculateEmi(
            Double loanAmount,
            Double interestRate,
            Integer tenureMonths);
}
	


