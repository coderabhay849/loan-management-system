package com.example.loanmanagement.service.serviceimpl;
	
	import com.example.loanmanagement.dto.LoanApplicationRequest;
	import com.example.loanmanagement.dto.LoanApplicationResponse;
import com.example.loanmanagement.dto.LoanReviewRequest;
import com.example.loanmanagement.entity.EmiSchedule;
import com.example.loanmanagement.entity.LoanApplication;
	import com.example.loanmanagement.entity.LoanStatus;
	import com.example.loanmanagement.entity.LoanType;
	import com.example.loanmanagement.entity.User;
import com.example.loanmanagement.repository.EmiScheduleRepository;
import com.example.loanmanagement.repository.LoanApplicationRepository;
	import com.example.loanmanagement.repository.LoanTypeRepository;
	import com.example.loanmanagement.repository.UserRepository;
	import com.example.loanmanagement.service.LoanApplicationService;
import com.example.loanmanagement.service.LoanHistoryService;
import com.example.loanmanagement.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

	import java.time.LocalDateTime;
	import java.util.List;

	@Service
	public class LoanApplicationServiceImpl
	        implements LoanApplicationService {
		
		private static final Logger log =
		        LoggerFactory.getLogger(LoanApplicationServiceImpl.class);

	    private final LoanApplicationRepository loanApplicationRepository;
	    private final LoanTypeRepository loanTypeRepository;
	    private final UserRepository userRepository;
	    private final EmiScheduleRepository emiScheduleRepository;
	    private final LoanHistoryService loanHistoryService;

	    public LoanApplicationServiceImpl(
	            LoanApplicationRepository loanApplicationRepository,
	            UserRepository userRepository,
	            LoanTypeRepository loanTypeRepository,
	            EmiScheduleRepository emiScheduleRepository,
	            LoanHistoryService loanHistoryService) {

	        this.loanApplicationRepository = loanApplicationRepository;
	        this.userRepository = userRepository;
	        this.loanTypeRepository = loanTypeRepository;
	        this.emiScheduleRepository = emiScheduleRepository;
	        this.loanHistoryService = loanHistoryService;
	    }
	    	
	    @Override
	    public LoanApplicationResponse applyForLoan(
	            LoanApplicationRequest request,
	            String customerEmail) {

	        User customer = userRepository
	                .findByEmail(customerEmail)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Customer not found"));
	        log.info("Loan application started for customer: {}", customerEmail);
	        LoanType loanType = loanTypeRepository
	                .findByIdAndDeletedFalse(request.getLoanTypeId())
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Loan type not found"));

	        if (!loanType.isActive()) {
	            throw new ResourceNotFoundException("Loan type is not active");
	        }
        
	        if (request.getLoanAmount() > loanType.getMaxAmount()) {
	            throw new ResourceNotFoundException(
	                    "Loan amount exceeds maximum allowed amount");
	        }

	        if (request.getTenureMonths()
	                > loanType.getMaxTenureMonths()) {

	            throw new ResourceNotFoundException(
	                    "Loan tenure exceeds maximum allowed tenure");
	        }

	        LoanApplication application = new LoanApplication();

	        application.setCustomer(customer);
	        application.setLoanType(loanType);
	        application.setLoanAmount(request.getLoanAmount());
	        application.setTenureMonths(request.getTenureMonths());
	        application.setInterestRate(loanType.getInterestRate());
	        application.setStatus(LoanStatus.PENDING);
	        application.setAppliedAt(LocalDateTime.now());

	        LoanApplication savedApplication =
	                loanApplicationRepository.save(application);
	       
	        loanHistoryService.addHistory(
	        		savedApplication.getId(),
	                "LOAN_APPLIED",
	                "Loan application submitted",
	                customerEmail
	        );
	        log.info(
	                "Loan application created successfully. Loan ID: {}",
	                savedApplication.getId()
	        );
	        return mapToResponse(savedApplication);
	        
	    }

	    @Override
	    public List<LoanApplicationResponse> getMyLoans(
	            String customerEmail) {

	        User customer = userRepository
	                .findByEmail(customerEmail)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Customer not found"));

	        return loanApplicationRepository
	                .findByCustomerId(customer.getId())
	                .stream()
	                .map(this::mapToResponse)
	                .toList();
	    }

	    @Override
	    public LoanApplicationResponse getLoanById(
	            Long id,
	            String customerEmail) {

	        User customer = userRepository
	                .findByEmail(customerEmail)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Customer not found"));

	        LoanApplication application =
	                loanApplicationRepository.findById(id)
	                        .orElseThrow(() ->
	                                new ResourceNotFoundException(
	                                        "Loan application not found"));

	        if (!application.getCustomer()
	                .getId()
	                .equals(customer.getId())) {

	            throw new ResourceNotFoundException(
	                    "You are not allowed to access this loan");
	        }

	        return mapToResponse(application);
	    }

	    private LoanApplicationResponse mapToResponse(
	            LoanApplication application) {

	        return new LoanApplicationResponse(
	                application.getId(),
	                application.getCustomer().getEmail(),
	                application.getLoanType().getName(),
	                application.getLoanAmount(),
	                application.getTenureMonths(),
	                application.getInterestRate(),
	                application.getStatus(),
	                application.getRemarks(),
	                application.getAppliedAt()
	        );
	    }
	    
	    
	    @Override
	    public LoanApplicationResponse reviewLoan(
	            Long loanId,
	            LoanReviewRequest request,
	            String officerEmail) {
	    	
	    	log.info(
	    	        "Loan review started. Loan ID: {}, Officer: {}",
	    	        loanId,
	    	        officerEmail
	    	);

	        LoanApplication loan = loanApplicationRepository.findById(loanId)
	                .orElseThrow(() -> new ResourceNotFoundException("Loan application not found"));

	        if (loan.getStatus() != LoanStatus.PENDING &&
	            loan.getStatus() != LoanStatus.UNDER_REVIEW) {
	            throw new ResourceNotFoundException(
	                    "Loan cannot be reviewed in current status");
	        }

	        loan.setStatus(LoanStatus.UNDER_REVIEW);
	        loan.setRemarks(request.getRemarks());
	        loan.setUpdatedAt(LocalDateTime.now());

	        if (Boolean.TRUE.equals(request.getApproved())) {
	            loan.setStatus(LoanStatus.APPROVED);
	        } else {
	            loan.setStatus(LoanStatus.REJECTED);
	        }

	        LoanApplication updatedLoan =
	                loanApplicationRepository.save(loan);

	        String action = Boolean.TRUE.equals(request.getApproved())
	                ? "LOAN_APPROVED"
	                : "LOAN_REJECTED";
	        log.info(
	                "Loan review completed. Loan ID: {}, Approved: {}",
	                loanId,
	                request.getApproved()
	        );

	        loanHistoryService.addHistory(
	                updatedLoan.getId(),
	                action,
	                request.getRemarks(),
	                officerEmail
	        );

	        return mapToResponse(updatedLoan);
	    }
	    
	    @Override
	    public LoanApplicationResponse getLoanForOfficer(Long loanId) {

	        LoanApplication loan = loanApplicationRepository.findById(loanId)
	                .orElseThrow(() -> new ResourceNotFoundException("Loan application not found"));

	        return mapToResponse(loan);
	    }
	    @Override
	    public void checkAndCloseLoan(Long loanId) {

	        LoanApplication loan = loanApplicationRepository.findById(loanId)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Loan application not found"));

	        if (loan.getStatus() != LoanStatus.APPROVED &&
	            loan.getStatus() != LoanStatus.ACTIVE) {
	            throw new ResourceNotFoundException(
	                    "Only active or approved loans can be closed");
	        }

	        List<EmiSchedule> schedule =
	                emiScheduleRepository
	                        .findByLoanApplicationIdOrderByInstallmentNumberAsc(
	                                loanId);

	        if (schedule.isEmpty()) {
	            throw new ResourceNotFoundException("EMI schedule not found");
	        }

	        boolean allPaid = schedule.stream()
	                .allMatch(EmiSchedule::isPaid);

	        if (!allPaid) {
	            throw new ResourceNotFoundException(
	                    "Loan cannot be closed. All EMIs are not paid");
	        }

	        loan.setStatus(LoanStatus.CLOSED);
	        loan.setUpdatedAt(LocalDateTime.now());

	        loanApplicationRepository.save(loan);

	        loanHistoryService.addHistory(
	                loanId,
	                "LOAN_CLOSED",
	                "Loan closed successfully",
	                "LOAN_OFFICER"
	        );
	    }
	    
	    
	    
	    
	    
	}

