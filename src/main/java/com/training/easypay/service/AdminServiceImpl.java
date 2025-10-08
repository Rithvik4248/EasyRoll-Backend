package com.training.easypay.service;

import com.training.easypay.model.Admin;
import com.training.easypay.model.Employee;
import com.training.easypay.repositories.AdminRepository;
import com.training.easypay.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AdminRepository adminRepository;

    private void checkPermissions(Long adminId, String requiredRole) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        if (!admin.getDesignation().equals(requiredRole)) {
            throw new RuntimeException("You do not have permission to perform this action.");
        }
    }

    @Override
    public Optional<Employee> getEmployeeById(Long employeeId, Long adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        if (admin.getDesignation().equals("ADMIN") || admin.getDesignation().equals("HR")) {
            return employeeRepository.findById(employeeId);
        }
        throw new RuntimeException("You do not have permission to perform this action.");
    }

    @Override
    public Employee createEmployee(Employee employee, Long adminId) {
        checkPermissions(adminId, "ADMIN");
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long employeeId, Employee employeeDetails, Long adminId) {
        checkPermissions(adminId, "ADMIN");
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setPhone(employeeDetails.getPhone());
        employee.setDesignation(employeeDetails.getDesignation());
        employee.setSalary(employeeDetails.getSalary());
        employee.setStatus(employeeDetails.getStatus());

        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long employeeId, Long adminId) {
        checkPermissions(adminId, "ADMIN");
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public Employee updateEmployeeDesignation(Long employeeId, String designation, Long adminId) {
        checkPermissions(adminId, "ADMIN");
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employee.setDesignation(designation);
        return employeeRepository.save(employee);
    }
}
