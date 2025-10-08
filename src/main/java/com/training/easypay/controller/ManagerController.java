package com.training.easypay.controller;

import com.training.easypay.model.LeaveRequest;
import com.training.easypay.model.PayrollData;
import com.training.easypay.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("/payrolls")
    public ResponseEntity<Map<Long, List<PayrollData>>> getGroupedAndSortedPayrollData() {
        return ResponseEntity.ok(managerService.getGroupedAndSortedPayrollData());
    }

    @PutMapping("/leaverequests/{leaveRequestId}")
    public ResponseEntity<LeaveRequest> updateLeaveRequestStatus(
            @PathVariable Long leaveRequestId,
            @RequestParam String status,
            @RequestParam(required = false) String reason) {
        return ResponseEntity.ok(managerService.updateLeaveRequestStatus(leaveRequestId, status, reason));
    }
}
