package com.example.loanmanagement.dto;

public class LoanReportResponse {

    private long totalLoans;
    private long pendingLoans;
    private long underReviewLoans;
    private long approvedLoans;
    private long rejectedLoans;
    private long activeLoans;
    private long closedLoans;

    public LoanReportResponse() {
    }

    public LoanReportResponse(
            long totalLoans,
            long pendingLoans,
            long underReviewLoans,
            long approvedLoans,
            long rejectedLoans,
            long activeLoans,
            long closedLoans) {

        this.totalLoans = totalLoans;
        this.pendingLoans = pendingLoans;
        this.underReviewLoans = underReviewLoans;
        this.approvedLoans = approvedLoans;
        this.rejectedLoans = rejectedLoans;
        this.activeLoans = activeLoans;
        this.closedLoans = closedLoans;
    }

    public long getTotalLoans() {
        return totalLoans;
    }

    public long getPendingLoans() {
        return pendingLoans;
    }

    public long getUnderReviewLoans() {
        return underReviewLoans;
    }

    public long getApprovedLoans() {
        return approvedLoans;
    }

    public long getRejectedLoans() {
        return rejectedLoans;
    }

    public long getActiveLoans() {
        return activeLoans;
    }

    public long getClosedLoans() {
        return closedLoans;
    }
}