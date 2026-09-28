package com.example.loanmanagement.entity;

	import jakarta.persistence.*;

	import java.time.LocalDateTime;

	@Entity
	@Table(name = "loan_applications")
	public class LoanApplication {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "customer_id", nullable = false)
	    private User customer;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "loan_type_id", nullable = false)
	    private LoanType loanType;

	    @Column(nullable = false)
	    private Double loanAmount;

	    @Column(nullable = false)
	    private Integer tenureMonths;

	    @Column(nullable = false)
	    private Double interestRate;

	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private LoanStatus status;

	    @Column(length = 1000)
	    private String remarks;

	    @Column(nullable = false)
	    private LocalDateTime appliedAt;

	    private LocalDateTime updatedAt;

	    public LoanApplication() {
	    }

	    public Long getId() {
	        return id;
	    }

	    public User getCustomer() {
	        return customer;
	    }

	    public void setCustomer(User customer) {
	        this.customer = customer;
	    }

	    public LoanType getLoanType() {
	        return loanType;
	    }

	    public void setLoanType(LoanType loanType) {
	        this.loanType = loanType;
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

	    public Double getInterestRate() {
	        return interestRate;
	    }

	    public void setInterestRate(Double interestRate) {
	        this.interestRate = interestRate;
	    }

	    public LoanStatus getStatus() {
	        return status;
	    }

	    public void setStatus(LoanStatus status) {
	        this.status = status;
	    }

	    public String getRemarks() {
	        return remarks;
	    }

	    public void setRemarks(String remarks) {
	        this.remarks = remarks;
	    }

	    public LocalDateTime getAppliedAt() {
	        return appliedAt;
	    }

	    public void setAppliedAt(LocalDateTime appliedAt) {
	        this.appliedAt = appliedAt;
	    }

	    public LocalDateTime getUpdatedAt() {
	        return updatedAt;
	    }

	    public void setUpdatedAt(LocalDateTime updatedAt) {
	        this.updatedAt = updatedAt;
	    }
	}

