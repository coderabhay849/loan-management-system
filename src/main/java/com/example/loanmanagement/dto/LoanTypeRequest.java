package com.example.loanmanagement.dto;

	import jakarta.validation.constraints.DecimalMin;
	import jakarta.validation.constraints.NotBlank;
	import jakarta.validation.constraints.NotNull;
	import jakarta.validation.constraints.Positive;

	public class LoanTypeRequest {

	    @NotBlank(message = "Loan type name is required")
	    private String name;

	    private String description;

	    @NotNull(message = "Interest rate is required")
	    @DecimalMin(value = "0.0", message = "Interest rate cannot be negative")
	    private Double interestRate;

	    @NotNull(message = "Maximum amount is required")
	    @Positive(message = "Maximum amount must be greater than 0")
	    private Double maxAmount;

	    @NotNull(message = "Maximum tenure is required")
	    @Positive(message = "Maximum tenure must be greater than 0")
	    private Integer maxTenureMonths;

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public Double getInterestRate() {
	        return interestRate;
	    }

	    public void setInterestRate(Double interestRate) {
	        this.interestRate = interestRate;
	    }

	    public Double getMaxAmount() {
	        return maxAmount;
	    }

	    public void setMaxAmount(Double maxAmount) {
	        this.maxAmount = maxAmount;
	    }

	    public Integer getMaxTenureMonths() {
	        return maxTenureMonths;
	    }

	    public void setMaxTenureMonths(Integer maxTenureMonths) {
	        this.maxTenureMonths = maxTenureMonths;
	    }
	}
