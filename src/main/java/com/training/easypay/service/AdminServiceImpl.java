package com.training.easypay.service;

import com.training.easypay.exceptions.ResourceNotFoundException;
import com.training.easypay.model.Employee;
import com.training.easypay.model.PayrollPolicy;
import com.training.easypay.repositories.EmployeeRepository;
import com.training.easypay.repositories.PayrollPolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final EmployeeRepository employeeRepository;
    private final PayrollPolicyRepository payrollPolicyRepository;

    @Autowired
    public AdminServiceImpl(EmployeeRepository employeeRepository, PayrollPolicyRepository payrollPolicyRepository) {
        this.employeeRepository = employeeRepository;
        this.payrollPolicyRepository = payrollPolicyRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(Long employeeId, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setPhone(employeeDetails.getPhone());
        employee.setDesignation(employeeDetails.getDesignation());
        employee.setSalary(employeeDetails.getSalary());

        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        employeeRepository.delete(employee);
    }

    @Override
    public PayrollPolicy createPayrollPolicy(PayrollPolicy payrollPolicy) {
        return payrollPolicyRepository.save(payrollPolicy);
    }

    @Override
    public PayrollPolicy updatePayrollPolicy(Long policyId, PayrollPolicy policyDetails) {
        PayrollPolicy policy = payrollPolicyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Payroll Policy not found with id: " + policyId));

        policy.setPolicyName(policyDetails.getPolicyName());
        policy.setTaxPercentage(policyDetails.getTaxPercentage());
        policy.setHraPercentage(policyDetails.getHraPercentage());
        policy.setAllowancesPercentage(policyDetails.getAllowancesPercentage());
        policy.setPaidLeaveDays(policyDetails.getPaidLeaveDays());

        return payrollPolicyRepository.save(policy);
    }
}
