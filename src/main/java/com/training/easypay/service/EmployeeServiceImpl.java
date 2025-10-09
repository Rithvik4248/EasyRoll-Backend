package com.training.easypay.service;

import com.training.easypay.Exceptions.EmployeeNotFoundException;
import com.training.easypay.model.*;
import com.training.easypay.repositories.EmployeeRepository;
import com.training.easypay.repositories.LeaveRequestRepository;
import com.training.easypay.repositories.PayrollDataRepository;
import com.training.easypay.repositories.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repo;
    private final LeaveRequestRepository leaveRequestRepository;
    private final PayrollDataRepository payrollDataRepository;
    private final PayrollRepository payrollRepository; // Assuming this repository exists

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repo, LeaveRequestRepository leaveRequestRepository, PayrollDataRepository payrollDataRepository, PayrollRepository payrollRepository) {
        this.repo = repo;
        this.leaveRequestRepository = leaveRequestRepository;
        this.payrollDataRepository = payrollDataRepository;
        this.payrollRepository = payrollRepository;
    }

    @Override
    public Employee save(Employee employee) {
        return repo.save(employee);
    }

    @Override
    public List<Employee> findAll() {
        return repo.findAll();
    }

    @Override
    public Employee findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found."));
    }

    @Override
    public Employee findByEmail(String email) {
        return repo.findByEmail(email).orElseThrow(() -> new EmployeeNotFoundException("Employee not found with email: " + email));
    }

    @Override
    public void activateEmployee(Long id) {
        Employee employee = findById(id);
        employee.setStatus(EmployeeStatus.ACTIVE);
        repo.save(employee);
    }

    @Override
    public void deactivateEmployee(Long id) {
        Employee employee = findById(id);
        employee.setStatus(EmployeeStatus.INACTIVE);
        repo.save(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return repo.save(employee);
    }

    @Override
    public LeaveRequest submitLeaveRequest(LeaveRequest request) {
        return leaveRequestRepository.save(request);
    }

    @Override
    public List<LeaveRequest> getLeaveRequestsByEmployeeId(Long employeeId) {
        return leaveRequestRepository.findByEmployeeId(employeeId);
    }

    @Override
    public List<Payroll> getPayrollsByEmployeeId(Long employeeId) {
        List<PayrollData> payrollData = payrollDataRepository.findByEmployeeId(employeeId);
        return payrollData.stream()
                .map(PayrollData::getPayroll)
                .collect(Collectors.toList());
    }
}