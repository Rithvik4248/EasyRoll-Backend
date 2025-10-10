package com.training.easypay.controller;

import com.training.easypay.model.Employee;
import com.training.easypay.model.PayrollPolicy;
import com.training.easypay.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Employee Management Endpoints
    @GetMapping("/employees")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = adminService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/employees/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Employee updatedEmployee = adminService.updateEmployee(id, employeeDetails);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/employees/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable Long id) {
        adminService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Payroll Policy Management Endpoints
    @PostMapping("/payroll/policies")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
    public ResponseEntity<PayrollPolicy> createPayrollPolicy(@RequestBody PayrollPolicy payrollPolicy) {
        PayrollPolicy newPolicy = adminService.createPayrollPolicy(payrollPolicy);
        return new ResponseEntity<>(newPolicy, HttpStatus.CREATED);
    }

    @PutMapping("/payroll/policies/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
    public ResponseEntity<PayrollPolicy> updatePayrollPolicy(@PathVariable Long id, @RequestBody PayrollPolicy policyDetails) {
        PayrollPolicy updatedPolicy = adminService.updatePayrollPolicy(id, policyDetails);
        return ResponseEntity.ok(updatedPolicy);
    }
}
