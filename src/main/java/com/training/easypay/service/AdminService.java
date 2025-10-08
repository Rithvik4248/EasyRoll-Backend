package com.training.easypay.service;

import com.training.easypay.model.Employee;

import java.util.Optional;

public interface AdminService {
    Optional<Employee> getEmployeeById(Long employeeId, Long adminId);
    Employee createEmployee(Employee employee, Long adminId);
    Employee updateEmployee(Long employeeId, Employee employeeDetails, Long adminId);
    void deleteEmployee(Long employeeId, Long adminId);
    Employee updateEmployeeDesignation(Long employeeId, String designation, Long adminId);
}
