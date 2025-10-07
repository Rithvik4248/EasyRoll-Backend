package com.training.easypay.controller;

import com.training.easypay.dtos.EmployeeDTO;
import com.training.easypay.dtos.LeaveRequestDTO;
import com.training.easypay.dtos.PayrollDataDTO;
import com.training.easypay.model.Employee;
import com.training.easypay.model.LeaveRequest;
import com.training.easypay.model.PayrollData;
import com.training.easypay.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee")
@PreAuthorize("hasAuthority('EMPLOYEE')")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeController(EmployeeService employeeService, ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.findById(id);
        // Manual mapping to avoid overwriting sensitive fields
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPhone(employeeDTO.getPhone());
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        return ResponseEntity.ok(modelMapper.map(updatedEmployee, EmployeeDTO.class));
    }

    @PostMapping("/leave-requests")
    public ResponseEntity<LeaveRequestDTO> submitLeaveRequest(@RequestBody LeaveRequestDTO leaveRequestDTO) {
        LeaveRequest leaveRequest = modelMapper.map(leaveRequestDTO, LeaveRequest.class);
        LeaveRequest newLeaveRequest = employeeService.submitLeaveRequest(leaveRequest);
        return ResponseEntity.ok(modelMapper.map(newLeaveRequest, LeaveRequestDTO.class));
    }

    @GetMapping("/{id}/leave-requests")
    public ResponseEntity<List<LeaveRequestDTO>> getLeaveRequests(@PathVariable Long id) {
        List<LeaveRequest> leaveRequests = employeeService.getLeaveRequestsByEmployeeId(id);
        List<LeaveRequestDTO> leaveRequestDTOs = leaveRequests.stream()
                .map(request -> modelMapper.map(request, LeaveRequestDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(leaveRequestDTOs);
    }

    @GetMapping("/{id}/payroll-data")
    public ResponseEntity<PayrollDataDTO> getPayrollData(@PathVariable Long id) {
        PayrollData payrollData = employeeService.getPayrollData(id);
        return ResponseEntity.ok(modelMapper.map(payrollData, PayrollDataDTO.class));
    }
}
