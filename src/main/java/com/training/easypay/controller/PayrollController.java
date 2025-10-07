package com.training.easypay.controller;

import com.training.easypay.dtos.PayrollRequestDTO;
import com.training.easypay.model.Payroll;
import com.training.easypay.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payrolls")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    @PostMapping("/calculate")
    public ResponseEntity<Payroll> calculatePayroll(@RequestBody PayrollRequestDTO payrollRequestDTO) {
        Payroll payroll = payrollService.calculatePayroll(payrollRequestDTO.getEmployeeId(), payrollRequestDTO.getMonth(), payrollRequestDTO.getYear());
        return ResponseEntity.ok(payroll);
    }
}
