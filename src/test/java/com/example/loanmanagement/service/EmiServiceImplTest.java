
	package com.example.loanmanagement.service;

	import com.example.loanmanagement.dto.EmiResponse;
	import com.example.loanmanagement.service.serviceimpl.EmiServiceImpl;
	import org.junit.jupiter.api.Test;

	import static org.junit.jupiter.api.Assertions.*;

	class EmiServiceImplTest {

	    private final EmiServiceImpl emiService = new EmiServiceImpl();

	    @Test
	    void calculateEmi_shouldReturnResult() {

	        EmiResponse response =
	                emiService.calculateEmi(
	                        100000.0,
	                        12.0,
	                        12
	                );

	        assertNotNull(response);
	        assertTrue(response.getMonthlyEmi() > 0);
	        assertTrue(response.getTotalPayment() > 0);
	        assertTrue(response.getTotalInterest() > 0);
	    }

	    @Test
	    void calculateEmi_shouldThrowExceptionForInvalidAmount() {

	        assertThrows(
	                RuntimeException.class,
	                () -> emiService.calculateEmi(
	                        0.0,
	                        12.0,
	                        12
	                )
	        );
	    }
	}

