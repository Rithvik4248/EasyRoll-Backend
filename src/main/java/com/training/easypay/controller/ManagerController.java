package com.training.easypay.controller;

import com.training.easypay.model.LeaveRequest;
import com.training.easypay.model.Payroll;
import com.training.easypay.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
@CrossOrigin(origins = "*")
public class ManagerController {

    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/leave-requests")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<List<LeaveRequest>> getPendingLeaveRequests() {
        List<LeaveRequest> leaveRequests = managerService.getPendingLeaveRequests();
        return ResponseEntity.ok(leaveRequests);
    }

    @PutMapping("/leave-requests/{id}/approve")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<LeaveRequest> approveLeaveRequest(@PathVariable Long id) {
        LeaveRequest updatedRequest = managerService.approveLeaveRequest(id);
        return ResponseEntity.ok(updatedRequest);
    }

    @PutMapping("/leave-requests/{id}/reject")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<LeaveRequest> rejectLeaveRequest(@PathVariable Long id) {
        LeaveRequest updatedRequest = managerService.rejectLeaveRequest(id);
        return ResponseEntity.ok(updatedRequest);
    }

    @GetMapping("/payrolls")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<List<Payroll>> getAllPayrollsForPeriod(@RequestParam String period) {
        List<Payroll> payrolls = managerService.getAllPayrollsForPeriod(period);
        return ResponseEntity.ok(payrolls);
    }
}
