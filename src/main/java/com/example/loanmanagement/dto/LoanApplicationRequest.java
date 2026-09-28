package com.example.loanmanagement.dto;

	import jakarta.validation.constraints.NotNull;
	import jakarta.validation.constraints.Positive;

	public class LoanApplicationRequest {

	    @NotNull(message = "Loan type is required")
	    private Long loanTypeId;

	    @NotNull(message = "Loan amount is required")
	    @Positive(message = "Loan amount must be greater than 0")
	    private Double loanAmount;

	    @NotNull(message = "Tenure is required")
	    @Positive(message = "Tenure must be greater than 0")
	    private Integer tenureMonths;

	    public Long getLoanTypeId() {
	        return loanTypeId;
	    }

	    public void setLoanTypeId(Long loanTypeId) {
	        this.loanTypeId = loanTypeId;
	    }

	    public Double getLoanAmount() {
	        return loanAmount;
	    }

	    public void setLoanAmount(Double loanAmount) {
	        this.loanAmount = loanAmount;
	    }

	    public Integer getTenureMonths() {
	        return tenureMonths;
	    }

	    public void setTenureMonths(Integer tenureMonths) {
	        this.tenureMonths = tenureMonths;
	    }
	}


