package com.example.loanmanagement.service;

import com.example.loanmanagement.dto.AuthResponse;
import com.example.loanmanagement.dto.LoginRequest;
import com.example.loanmanagement.dto.LoginResponse;
import com.example.loanmanagement.dto.RegisterRequest;

public interface AuthService {
	
	 AuthResponse register(RegisterRequest request);
	 
	 LoginResponse login(LoginRequest request);
    
	 
}
