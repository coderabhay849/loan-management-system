package com.example.loanmanagement.service.serviceimpl;
	
	import com.example.loanmanagement.dto.AuthResponse;
	import com.example.loanmanagement.dto.RegisterRequest;
	import com.example.loanmanagement.entity.Role;
	import com.example.loanmanagement.entity.User;
	import com.example.loanmanagement.repository.RoleRepository;
	import com.example.loanmanagement.repository.UserRepository;
	import com.example.loanmanagement.service.AuthService;
	import org.springframework.security.crypto.password.PasswordEncoder;
	import org.springframework.stereotype.Service;
	import com.example.loanmanagement.dto.LoginRequest;
	import com.example.loanmanagement.dto.LoginResponse;
	import com.example.loanmanagement.security.JwtService;
	
	@Service
	public class AuthServiceImpl implements AuthService {

	    private final UserRepository userRepository;
	    private final RoleRepository roleRepository;
	    private final PasswordEncoder passwordEncoder;
	    private final JwtService jwtService;

	    public AuthServiceImpl(UserRepository userRepository,
                RoleRepository roleRepository,
                PasswordEncoder passwordEncoder,
                JwtService jwtService) {

this.userRepository = userRepository;
this.roleRepository = roleRepository;
this.passwordEncoder = passwordEncoder;
this.jwtService = jwtService;
}

	    @Override
	    public AuthResponse register(RegisterRequest request) {

	        if (userRepository.existsByEmail(request.getEmail())) {
	            throw new RuntimeException("Email already registered");
	        }

	        Role customerRole = roleRepository.findByName("CUSTOMER")
	                .orElseThrow(() -> new RuntimeException("CUSTOMER role not found"));

	        User user = new User();

	        user.setUsername(request.getUsername());
	        user.setEmail(request.getEmail());
	        user.setPassword(passwordEncoder.encode(request.getPassword()));
	        user.setRole(customerRole);
	        user.setEnabled(true);

	        userRepository.save(user);

	        return new AuthResponse("Customer registered successfully");
	    }
	   
	    @Override
	    public LoginResponse login(LoginRequest request) {

	        User user = userRepository.findByEmail(request.getEmail())
	                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

	        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
	            throw new RuntimeException("Invalid email or password");
	        }

	        if (!user.isEnabled()) {
	            throw new RuntimeException("User account is disabled");
	        }

	        String token = jwtService.generateToken(
	                user.getEmail(),
	                user.getRole().getName()
	        );

	        return new LoginResponse(
	                token,
	                user.getEmail(),
	                user.getRole().getName()
	        );
	    }
	    
	}

