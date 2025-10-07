package com.training.easypay.service;

import com.training.easypay.Exceptions.EmployeeNotFoundException;
import com.training.easypay.model.Employee;
import com.training.easypay.model.EmployeeStatus;
import com.training.easypay.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepo repo;

    public Employee save(Employee employee) {
        return repo.save(employee);
    }

    public List<Employee> findAll() {
        return repo.findAll();
    }

    public Employee findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + id));
    }

    public void activateEmployee(Long id){
        Employee emp = repo.findById(id).orElseThrow(()->new EmployeeNotFoundException("Employee not found with ID: " + id));
        emp.setStatus(EmployeeStatus.ACTIVE);
        repo.save(emp);
    }

    public void deactivateEmployee(Long id){
        Employee emp = repo.findById(id).orElseThrow(()->new EmployeeNotFoundException("Employee not found with ID: " + id));
        emp.setStatus(EmployeeStatus.INACTIVE);
        repo.save(emp);
    }
}
