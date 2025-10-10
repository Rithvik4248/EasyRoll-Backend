package com.training.easypay.service;

import com.training.easypay.model.Payroll;

import java.util.List;

public interface PayrollService {

    List<Payroll> calculatePayrollForPeriod(String payPeriod);

    void processPaymentsForPeriod(String payPeriod);
}
