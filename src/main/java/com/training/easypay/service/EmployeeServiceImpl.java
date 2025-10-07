package com.training.easypay.service;

import com.training.easypay.Exceptions.EmployeeNotFoundException;
import com.training.easypay.model.Employee;
import com.training.easypay.model.EmployeeStatus;
import com.training.easypay.model.LeaveRequest;
import com.training.easypay.model.PayrollData;
import com.training.easypay.repositories.EmployeeRepository;
import com.training.easypay.repositories.LeaveRequestRepository;
import com.training.easypay.repositories.PayrollDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository repo;
    private final LeaveRequestRepository leaveRequestRepository;
    private final PayrollDataRepository payrollDataRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repo, LeaveRequestRepository leaveRequestRepository, PayrollDataRepository payrollDataRepository) {
        this.repo = repo;
        this.leaveRequestRepository = leaveRequestRepository;
        this.payrollDataRepository = payrollDataRepository;
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
        return repo.findById(id).orElseThrow(()->new EmployeeNotFoundException("Employee not found."));
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
    public PayrollData getPayrollData(Long employeeId) {
        return payrollDataRepository.findByEmployeeId(employeeId);
    }
}
