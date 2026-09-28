package com.example.loanmanagement.service;

	import com.example.loanmanagement.dto.LoanReportResponse;
	import com.example.loanmanagement.entity.LoanStatus;
	import com.example.loanmanagement.repository.LoanApplicationRepository;
	import com.example.loanmanagement.service.serviceimpl.LoanReportServiceImpl;
	import org.junit.jupiter.api.Test;

	import static org.junit.jupiter.api.Assertions.*;
	import static org.mockito.Mockito.*;

	class LoanReportServiceImplTest {

	    @Test
	    void getLoanReport_shouldReturnReport() {

	        LoanApplicationRepository repository =
	                mock(LoanApplicationRepository.class);

	        when(repository.count()).thenReturn(5L);
	        when(repository.countByStatus(LoanStatus.PENDING)).thenReturn(1L);
	        when(repository.countByStatus(LoanStatus.UNDER_REVIEW)).thenReturn(1L);
	        when(repository.countByStatus(LoanStatus.APPROVED)).thenReturn(1L);
	        when(repository.countByStatus(LoanStatus.REJECTED)).thenReturn(1L);
	        when(repository.countByStatus(LoanStatus.ACTIVE)).thenReturn(1L);
	        when(repository.countByStatus(LoanStatus.CLOSED)).thenReturn(0L);

	        LoanReportServiceImpl service =
	                new LoanReportServiceImpl(repository);

	        LoanReportResponse response = service.getLoanReport();

	        assertNotNull(response);
	        assertEquals(5L, response.getTotalLoans());
	        assertEquals(1L, response.getPendingLoans());
	        assertEquals(1L, response.getApprovedLoans());
	        assertEquals(0L, response.getClosedLoans());
	    }
	}
