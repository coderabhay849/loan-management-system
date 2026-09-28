package com.example.loanmanagement.controller;

	import com.example.loanmanagement.dto.AuthResponse;
import com.example.loanmanagement.dto.LoginRequest;
import com.example.loanmanagement.dto.LoginResponse;
import com.example.loanmanagement.dto.RegisterRequest;
	import com.example.loanmanagement.service.AuthService;

	import jakarta.validation.Valid;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;

	@RestController
	@RequestMapping("/api/auth")
	public class AuthController {

	    private final AuthService authService;

	    public AuthController(AuthService authService) {
	        this.authService = authService;
	    }

	    @PostMapping("/register")
	    public ResponseEntity<AuthResponse> register(
	            @Valid @RequestBody RegisterRequest request) {

	        return ResponseEntity
	                .status(HttpStatus.CREATED)
	                .body(authService.register(request));
	    }
	    @PostMapping("/login")
	    public ResponseEntity<LoginResponse> login(
	            @Valid @RequestBody LoginRequest request) {

	        return ResponseEntity.ok(authService.login(request));
	    }
	    
	}
	
