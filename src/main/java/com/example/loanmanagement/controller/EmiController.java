package com.example.loanmanagement.controller;

import com.example.loanmanagement.dto.EmiResponse;
import com.example.loanmanagement.service.EmiService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/officer/emi")
public class EmiController {

    private final EmiService emiService;

    public EmiController(EmiService emiService) {
        this.emiService = emiService;
    }

    @GetMapping("/calculate")
    public ResponseEntity<EmiResponse> calculateEmi(
            @RequestParam Double loanAmount,
            @RequestParam Double interestRate,
            @RequestParam Integer tenureMonths) {

        return ResponseEntity.ok(
                emiService.calculateEmi(
                        loanAmount,
                        interestRate,
                        tenureMonths));
    }
}

