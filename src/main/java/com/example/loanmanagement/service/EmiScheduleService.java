package com.example.loanmanagement.service;

import com.example.loanmanagement.dto.EmiResponse;
import com.example.loanmanagement.entity.EmiSchedule;

import java.util.List;

public interface EmiScheduleService {

    List<EmiSchedule> generateSchedule(Long loanId);

    List<EmiSchedule> getSchedule(Long loanId);
}