package com.training.easypay.service;

import com.training.easypay.model.Payroll;

public interface PayrollService {
    Payroll calculatePayroll(Long employeeId, int month, int year);
}
