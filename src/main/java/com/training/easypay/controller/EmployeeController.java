package com.training.easypay.controller;

import com.training.easypay.dtos.EmployeeDTO;
import com.training.easypay.dtos.LeaveRequestDTO;
import com.training.easypay.dtos.PayrollDTO;
import com.training.easypay.model.Employee;
import com.training.easypay.model.LeaveRequest;
import com.training.easypay.model.Payroll;
import com.training.easypay.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeController(EmployeeService employeeService, ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/me")
    public ResponseEntity<EmployeeDTO> getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Employee employee = employeeService.findByEmail(currentUserName);
        return ResponseEntity.ok(modelMapper.map(employee, EmployeeDTO.class));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('HR')")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<Employee> employees = employeeService.findAll();
        List<EmployeeDTO> employeeDTOs = employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(employeeDTOs);
    }

    @PutMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.findById(id);
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPhone(employeeDTO.getPhone());
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        return ResponseEntity.ok(modelMapper.map(updatedEmployee, EmployeeDTO.class));
    }

    @PostMapping("/{id}/leave-requests")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<LeaveRequestDTO> submitLeaveRequest(@PathVariable Long id, @RequestBody LeaveRequestDTO leaveRequestDTO) {
        LeaveRequest leaveRequest = modelMapper.map(leaveRequestDTO, LeaveRequest.class);
        leaveRequest.setEmployeeId(id); // Ensure the leave request is for the authenticated user
        LeaveRequest newLeaveRequest = employeeService.submitLeaveRequest(leaveRequest);
        return ResponseEntity.ok(modelMapper.map(newLeaveRequest, LeaveRequestDTO.class));
    }

    @GetMapping("/{id}/leave-requests")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<List<LeaveRequestDTO>> getLeaveRequests(@PathVariable Long id) {
        List<LeaveRequest> leaveRequests = employeeService.getLeaveRequestsByEmployeeId(id);
        List<LeaveRequestDTO> leaveRequestDTOs = leaveRequests.stream()
                .map(request -> modelMapper.map(request, LeaveRequestDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(leaveRequestDTOs);
    }

    @GetMapping("/{id}/payrolls")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<List<PayrollDTO>> getPayrolls(@PathVariable Long id) {
        List<Payroll> payrolls = employeeService.getPayrollsByEmployeeId(id);
        List<PayrollDTO> payrollDTOs = payrolls.stream()
                .map(payroll -> modelMapper.map(payroll, PayrollDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(payrollDTOs);
    }
}