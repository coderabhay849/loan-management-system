package com.example.loanmanagement.dto;

	import jakarta.validation.constraints.NotNull;
	import jakarta.validation.constraints.Size;

	public class LoanReviewRequest {

	    @NotNull(message = "Approval status is required")
	    private Boolean approved;

	    @Size(max = 1000, message = "Remarks cannot exceed 1000 characters")
	    private String remarks;

	    public Boolean getApproved() {
	        return approved;
	    }

	    public void setApproved(Boolean approved) {
	        this.approved = approved;
	    }

	    public String getRemarks() {
	        return remarks;
	    }

	    public void setRemarks(String remarks) {
	        this.remarks = remarks;
	    }
	

}
