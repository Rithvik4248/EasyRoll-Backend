package com.training.easypay.service;

import com.training.easypay.exceptions.ResourceNotFoundException;
import com.training.easypay.model.LeaveRequest;
import com.training.easypay.model.LeaveStatus;
import com.training.easypay.model.Payroll;
import com.training.easypay.repositories.LeaveRequestRepository;
import com.training.easypay.repositories.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final PayrollRepository payrollRepository;

    @Autowired
    public ManagerServiceImpl(LeaveRequestRepository leaveRequestRepository, PayrollRepository payrollRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.payrollRepository = payrollRepository;
    }

    @Override
    public List<LeaveRequest> getPendingLeaveRequests() {
        return leaveRequestRepository.findByStatus(LeaveStatus.PENDING);
    }

    @Override
    public LeaveRequest approveLeaveRequest(Long leaveRequestId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found with id: " + leaveRequestId));
        leaveRequest.setStatus(LeaveStatus.APPROVED);
        return leaveRequestRepository.save(leaveRequest);
    }

    @Override
    public LeaveRequest rejectLeaveRequest(Long leaveRequestId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found with id: " + leaveRequestId));
        leaveRequest.setStatus(LeaveStatus.REJECTED);
        return leaveRequestRepository.save(leaveRequest);
    }

    @Override
    public List<Payroll> getAllPayrollsForPeriod(String period) {
        return payrollRepository.findByPayPeriod(period);
    }
}
