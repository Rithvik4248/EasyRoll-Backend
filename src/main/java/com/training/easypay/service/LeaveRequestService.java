package com.training.easypay.service;

import com.training.easypay.model.LeaveRequest;

import java.util.List;

public interface LeaveRequestService {
    public LeaveRequest save(LeaveRequest leave);
    public List<LeaveRequest> findAll();
    public LeaveRequest findById(Long id);
    public List<LeaveRequest> getPendingLeaveRequests();
}
