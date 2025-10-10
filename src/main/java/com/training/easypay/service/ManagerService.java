package com.training.easypay.service;

import com.training.easypay.model.LeaveRequest;
import com.training.easypay.model.Payroll;

import java.util.List;

public interface ManagerService {

    List<LeaveRequest> getPendingLeaveRequests();

    LeaveRequest approveLeaveRequest(Long leaveRequestId);

    LeaveRequest rejectLeaveRequest(Long leaveRequestId);

    List<Payroll> getAllPayrollsForPeriod(String period);
}
