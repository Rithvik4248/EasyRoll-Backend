package com.training.easypay.controller;

import com.training.easypay.model.LeaveRequest;
import com.training.easypay.model.PayrollData;
import com.training.easypay.service.LeaveRequestServiceImpl;
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

    @Autowired
    private LeaveRequestServiceImpl leaveRequestService;

    /**
     * New endpoint to get a list of all pending leave requests.
     * A real-world application would filter by the currently logged-in manager.
     */
    @GetMapping("/leave-requests/pending")
    public ResponseEntity<List<LeaveRequest>> getPendingLeaveRequests() {
        List<LeaveRequest> pendingRequests = leaveRequestService.getPendingLeaveRequests();
        return ResponseEntity.ok(pendingRequests);
    }

    /**
     * Existing function for updating status, exposed via a PUT request.
     * The manager uses this to APPROVE or REJECT a specific request.
     * The URL includes the ID, and the status is passed as a request parameter.
     * Example URL: PUT /api/manager/leave-requests/101?status=APPROVED
     */
    @PutMapping("/leave-requests/{id}")
    public ResponseEntity<LeaveRequest> updateLeaveRequestStatus(
            @PathVariable("id") Long leaveRequestId,
            @RequestParam("status") String status) {

        LeaveRequest updatedRequest = managerService.updateLeaveRequestStatus(leaveRequestId, status);
        return ResponseEntity.ok(updatedRequest);
    }
}