package com.example.loanmanagement.controller;

	import com.example.loanmanagement.entity.EmiSchedule;
	import com.example.loanmanagement.service.EmiScheduleService;

	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;

	import java.util.List;

	@RestController
	@RequestMapping("/api/officer/loans")
	public class EmiScheduleController {

	    private final EmiScheduleService emiScheduleService;

	    public EmiScheduleController(EmiScheduleService emiScheduleService) {
	        this.emiScheduleService = emiScheduleService;
	    }

	    @PostMapping("/{loanId}/emi-schedule")
	    public ResponseEntity<List<EmiSchedule>> generateSchedule(
	            @PathVariable Long loanId) {

	        return ResponseEntity.ok(
	                emiScheduleService.generateSchedule(loanId));
	    }

	    @GetMapping("/{loanId}/emi-schedule")
	    public ResponseEntity<List<EmiSchedule>> getSchedule(
	            @PathVariable Long loanId) {

	        return ResponseEntity.ok(
	                emiScheduleService.getSchedule(loanId));
	    }
	}
