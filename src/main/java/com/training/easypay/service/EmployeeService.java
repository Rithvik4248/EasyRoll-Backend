package com.training.easypay.service;

import com.training.easypay.model.Employee;
import com.training.easypay.model.LeaveRequest;
import com.training.easypay.model.Payroll;

import java.util.List;

public interface EmployeeService {
    Employee save(Employee employee);

    List<Employee> findAll();

    Employee findById(Long id);

    Employee findByEmail(String email);

    void activateEmployee(Long id);

    void deactivateEmployee(Long id);

    Employee updateEmployee(Employee employee);

    LeaveRequest submitLeaveRequest(LeaveRequest request);

    List<LeaveRequest> getLeaveRequestsByEmployeeId(Long employeeId);

    List<Payroll> getPayrollsByEmployeeId(Long employeeId);
}
