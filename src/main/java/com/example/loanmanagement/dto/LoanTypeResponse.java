package com.example.loanmanagement.dto;

public class LoanTypeResponse {
	
	    private Long id;
	    private String name;
	    private String description;
	    private Double interestRate;
	    private Double maxAmount;
	    private Integer maxTenureMonths;
	    private boolean active;

	    public LoanTypeResponse(Long id,
	                            String name,
	                            String description,
	                            Double interestRate,
	                            Double maxAmount,
	                            Integer maxTenureMonths,
	                            boolean active) {

	        this.id = id;
	        this.name = name;
	        this.description = description;
	        this.interestRate = interestRate;
	        this.maxAmount = maxAmount;
	        this.maxTenureMonths = maxTenureMonths;
	        this.active = active;
	    }

	    public Long getId() {
	        return id;
	    }

	    public String getName() {
	        return name;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public Double getInterestRate() {
	        return interestRate;
	    }

	    public Double getMaxAmount() {
	        return maxAmount;
	    }

	    public Integer getMaxTenureMonths() {
	        return maxTenureMonths;
	    }

	    public boolean isActive() {
	        return active;
	    }
	}

