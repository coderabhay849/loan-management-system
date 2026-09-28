package com.example.loanmanagement.entity;

	import jakarta.persistence.*;

	@Entity
	@Table(name = "loan_types")
	public class LoanType {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false, unique = true)
	    private String name;

	    @Column(length = 500)
	    private String description;

	    @Column(nullable = false)
	    private Double interestRate;

	    @Column(nullable = false)
	    private Double maxAmount;

	    @Column(nullable = false)
	    private Integer maxTenureMonths;

	    @Column(nullable = false)
	    private boolean active = true;

	    @Column(nullable = false)
	    private boolean deleted = false;

	    public LoanType() {
	    }

	    public Long getId() {
	        return id;
	    }

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

	    public boolean isActive() {
	        return active;
	    }

	    public void setActive(boolean active) {
	        this.active = active;
	    }

	    public boolean isDeleted() {
	        return deleted;
	    }

	    public void setDeleted(boolean deleted) {
	        this.deleted = deleted;
	    }
	}
