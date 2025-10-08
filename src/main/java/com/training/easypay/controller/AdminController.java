package com.training.easypay.controller;

import com.training.easypay.model.Employee;
import com.training.easypay.model.PayrollPolicy;
import com.training.easypay.service.AdminService;
import com.training.easypay.service.PayrollPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PayrollPolicyService payrollPolicyService;

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long employeeId, @RequestHeader Long adminId) {
        return adminService.getEmployeeById(employeeId, adminId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee, @RequestHeader Long adminId) {
        return ResponseEntity.ok(adminService.createEmployee(employee, adminId));
    }

    @PutMapping("/employees/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long employeeId, @RequestBody Employee employeeDetails, @RequestHeader Long adminId) {
        return ResponseEntity.ok(adminService.updateEmployee(employeeId, employeeDetails, adminId));
    }

    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeId, @RequestHeader Long adminId) {
        adminService.deleteEmployee(employeeId, adminId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/employees/{employeeId}/designation")
    public ResponseEntity<Employee> updateEmployeeDesignation(@PathVariable Long employeeId, @RequestBody String designation, @RequestHeader Long adminId) {
        return ResponseEntity.ok(adminService.updateEmployeeDesignation(employeeId, designation, adminId));
    }

    @PostMapping("/policies")
    public ResponseEntity<PayrollPolicy> createPayrollPolicy(@RequestBody PayrollPolicy policy, @RequestHeader Long adminId) {
        return ResponseEntity.ok(payrollPolicyService.createPolicy(policy, adminId));
    }

    @PutMapping("/policies/{policyId}")
    public ResponseEntity<PayrollPolicy> updatePayrollPolicy(@PathVariable Long policyId, @RequestBody PayrollPolicy policyDetails, @RequestHeader Long adminId) {
        return ResponseEntity.ok(payrollPolicyService.updatePolicy(policyId, policyDetails, adminId));
    }

    @GetMapping("/policies/{policyName}")
    public ResponseEntity<PayrollPolicy> getPayrollPolicyByName(@PathVariable String policyName) {
        return ResponseEntity.ok(payrollPolicyService.getPolicyByName(policyName));
    }
}
