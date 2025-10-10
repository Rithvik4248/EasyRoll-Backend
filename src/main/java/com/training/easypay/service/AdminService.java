package com.training.easypay.service;

import com.training.easypay.model.Employee;
import com.training.easypay.model.PayrollPolicy;

import java.util.List;

public interface AdminService {

    // Employee Management
    List<Employee> getAllEmployees();

    Employee updateEmployee(Long employeeId, Employee employeeDetails);

    void deleteEmployee(Long employeeId);

    // Payroll Policy Management
    PayrollPolicy createPayrollPolicy(PayrollPolicy payrollPolicy);

    PayrollPolicy updatePayrollPolicy(Long policyId, PayrollPolicy policyDetails);
}
