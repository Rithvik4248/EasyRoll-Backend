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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeController(EmployeeService employeeService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<EmployeeDTO> registerEmployee(@RequestBody EmployeeDTO employeeDTO) {
        employeeDTO.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        Employee newEmployee = employeeService.save(employee);
        return ResponseEntity.ok(modelMapper.map(newEmployee, EmployeeDTO.class));
    }

    @GetMapping("/me")
    public ResponseEntity<EmployeeDTO> getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Employee employee = employeeService.findByEmail(currentUserName);
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        employeeDTO.setPassword(null); // Never send the password back to the client
        return ResponseEntity.ok(employeeDTO);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('HR')") // Assuming only Admin/HR can see all employees
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<Employee> employees = employeeService.findAll();
        List<EmployeeDTO> employeeDTOs = employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(employeeDTOs);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
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
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<LeaveRequestDTO> submitLeaveRequest(@RequestBody LeaveRequestDTO leaveRequestDTO) {
        LeaveRequest leaveRequest = modelMapper.map(leaveRequestDTO, LeaveRequest.class);
        LeaveRequest newLeaveRequest = employeeService.submitLeaveRequest(leaveRequest);
        return ResponseEntity.ok(modelMapper.map(newLeaveRequest, LeaveRequestDTO.class));
    }

    @GetMapping("/{id}/leave-requests")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<List<LeaveRequestDTO>> getLeaveRequests(@PathVariable Long id) {
        List<LeaveRequest> leaveRequests = employeeService.getLeaveRequestsByEmployeeId(id);
        List<LeaveRequestDTO> leaveRequestDTOs = leaveRequests.stream()
                .map(request -> modelMapper.map(request, LeaveRequestDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(leaveRequestDTOs);
    }

    @GetMapping("/{id}/payroll-data")
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<PayrollDataDTO> getPayrollData(@PathVariable Long id) {
        PayrollData payrollData = employeeService.getPayrollData(id);
        return ResponseEntity.ok(modelMapper.map(payrollData, PayrollDataDTO.class));
    }
}
