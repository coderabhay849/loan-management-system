package com.example.loanmanagement.service.serviceimpl;

import com.example.loanmanagement.dto.EmiResponse;
import com.example.loanmanagement.service.EmiService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

@Service
public class EmiServiceImpl implements EmiService {

    @Override
    public EmiResponse calculateEmi(
            Double loanAmount,
            Double interestRate,
            Integer tenureMonths) {

        if (loanAmount == null || loanAmount <= 0) {
            throw new RuntimeException("Loan amount must be greater than zero");
        }

        if (interestRate == null || interestRate < 0) {
            throw new RuntimeException("Interest rate cannot be negative");
        }

        if (tenureMonths == null || tenureMonths <= 0) {
            throw new RuntimeException("Tenure must be greater than zero");
        }

        double monthlyRate =
                interestRate / 12 / 100;

        double monthlyEmi;

        if (monthlyRate == 0) {
            monthlyEmi = loanAmount / tenureMonths;
        } else {
            double factor = Math.pow(
                    1 + monthlyRate,
                    tenureMonths);

            monthlyEmi =
                    loanAmount * monthlyRate * factor
                    / (factor - 1);
        }

        double totalPayment =
                monthlyEmi * tenureMonths;

        double totalInterest =
                totalPayment - loanAmount;

        return new EmiResponse(
                loanAmount,
                interestRate,
                tenureMonths,
                round(monthlyEmi),
                round(totalInterest),
                round(totalPayment)
        );
    }

    private double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

}
