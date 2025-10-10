package com.training.easypay.controller;

import com.training.easypay.model.Payroll;
import com.training.easypay.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payroll")
@CrossOrigin(origins = "*")
public class PayrollController {

    private final PayrollService payrollService;

    @Autowired
    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    @PostMapping("/calculate")
    @PreAuthorize("hasAuthority('PAYROLL_PROCESSOR')")
    public ResponseEntity<List<Payroll>> calculatePayroll(@RequestParam String payPeriod) {
        List<Payroll> payrolls = payrollService.calculatePayrollForPeriod(payPeriod);
        return ResponseEntity.ok(payrolls);
    }

    @PostMapping("/process-payments")
    @PreAuthorize("hasAuthority('PAYROLL_PROCESSOR')")
    public ResponseEntity<Void> processPayments(@RequestParam String payPeriod) {
        payrollService.processPaymentsForPeriod(payPeriod);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
