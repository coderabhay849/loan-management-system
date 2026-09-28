package com.example.loanmanagement.dto;

public class EmiResponse {

    private Double loanAmount;
    private Double interestRate;
    private Integer tenureMonths;
    private Double monthlyEmi;
    private Double totalInterest;
    private Double totalPayment;

    public EmiResponse() {
    }

    public EmiResponse(Double loanAmount, Double interestRate,
                       Integer tenureMonths, Double monthlyEmi,
                       Double totalInterest, Double totalPayment) {
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.tenureMonths = tenureMonths;
        this.monthlyEmi = monthlyEmi;
        this.totalInterest = totalInterest;
        this.totalPayment = totalPayment;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public Integer getTenureMonths() {
        return tenureMonths;
    }

    public Double getMonthlyEmi() {
        return monthlyEmi;
    }

    public Double getTotalInterest() {
        return totalInterest;
    }

    public Double getTotalPayment() {
        return totalPayment;
    }


}
