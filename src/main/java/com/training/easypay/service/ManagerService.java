package com.training.easypay.service;

import com.training.easypay.model.LeaveRequest;
import com.training.easypay.model.PayrollData;

import java.util.List;
import java.util.Map;

public interface ManagerService {
    List<PayrollData> getGroupedAndSortedPayrollData();
    LeaveRequest updateLeaveRequestStatus(Long leaveRequestId, String status);
}
