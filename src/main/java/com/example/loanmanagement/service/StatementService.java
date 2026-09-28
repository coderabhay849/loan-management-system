package com.example.loanmanagement.service;

	import org.springframework.core.io.Resource;

	public interface StatementService {

	    Resource generateStatement(Long loanId, String customerEmail);
	}

