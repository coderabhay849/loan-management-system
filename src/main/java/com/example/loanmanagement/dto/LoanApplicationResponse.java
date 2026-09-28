package com.example.loanmanagement.dto;

	import com.example.loanmanagement.entity.LoanStatus;

	import java.time.LocalDateTime;

	public class LoanApplicationResponse {

	    private Long id;
	    private String customerEmail;
	    private String loanType;
	    private Double loanAmount;
	    private Integer tenureMonths;
	    private Double interestRate;
	    private LoanStatus status;
	    private String remarks;
	    private LocalDateTime appliedAt;

	    public LoanApplicationResponse(
	            Long id,
	            String customerEmail,
	            String loanType,
	            Double loanAmount,
	            Integer tenureMonths,
	            Double interestRate,
	            LoanStatus status,
	            String remarks,
	            LocalDateTime appliedAt) {

	        this.id = id;
	        this.customerEmail = customerEmail;
	        this.loanType = loanType;
	        this.loanAmount = loanAmount;
	        this.tenureMonths = tenureMonths;
	        this.interestRate = interestRate;
	        this.status = status;
	        this.remarks = remarks;
	        this.appliedAt = appliedAt;
	    }

	    public Long getId() {
	        return id;
	    }

	    public String getCustomerEmail() {
	        return customerEmail;
	    }

	    public String getLoanType() {
	        return loanType;
	    }

	    public Double getLoanAmount() {
	        return loanAmount;
	    }

	    public Integer getTenureMonths() {
	        return tenureMonths;
	    }

	    public Double getInterestRate() {
	        return interestRate;
	    }

	    public LoanStatus getStatus() {
	        return status;
	    }

	    public String getRemarks() {
	        return remarks;
	    }

	    public LocalDateTime getAppliedAt() {
	        return appliedAt;
	    }
	}