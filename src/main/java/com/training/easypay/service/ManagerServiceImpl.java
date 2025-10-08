package com.training.easypay.service;

import com.training.easypay.model.Employee;
import com.training.easypay.model.LeaveRequest;
import com.training.easypay.model.LeaveStatus;
import com.training.easypay.model.PayrollData;
import com.training.easypay.repositories.LeaveRequestRepository;
import com.training.easypay.repositories.PayrollDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private PayrollDataRepository payrollDataRepository;

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Override
    public Map<Long, List<PayrollData>> getGroupedAndSortedPayrollData() {
        Sort sort = Sort.by(Sort.Order.asc("employee.id"), Sort.Order.desc("payroll.id"));
        List<PayrollData> payrollDataList = payrollDataRepository.findAll(sort);

        return payrollDataList.stream()
                .collect(Collectors.groupingBy(pd -> pd.getEmployee().getId()));
    }
   // this function is dependent on employee class please read this comment
    @Override
    public LeaveRequest updateLeaveRequestStatus(Long leaveRequestId, String status, String reason) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        // Only pending requests can be updated
        if (leaveRequest.getStatus() != LeaveStatus.PENDING) {
            throw new RuntimeException("Leave request is not in PENDING state");
        }

        // Get employee details
        Employee employee = leaveRequest.getEmployee();

        // AUTO-APPROVE if employee has enough leave balance
        if (employee.getLeaveBalance() >= leaveRequest.getNumberOfDays()) {
            // Deduct leave balance
            employee.setLeaveBalance(employee.getLeaveBalance() - leaveRequest.getNumberOfDays());
            leaveRequest.setStatus(LeaveStatus.APPROVED);
            leaveRequest.setReason("Auto-approved within allowed leave balance");

            employeeRepository.save(employee); // save updated balance
            return leaveRequestRepository.save(leaveRequest);
        }

        // Otherwise: manager’s decision (status passed as input)
        LeaveStatus newStatus = LeaveStatus.valueOf(status.toUpperCase());

        // If rejected, reason is mandatory
        if (newStatus == LeaveStatus.REJECTED && (reason == null || reason.trim().isEmpty())) {
            throw new RuntimeException("A reason is required for rejecting a leave request.");
        }

        // Update status
        leaveRequest.setStatus(newStatus);
        if (newStatus == LeaveStatus.REJECTED) {
            leaveRequest.setReason(reason);
        }

        // If manager approves but balance is not enough → reject
        if (newStatus == LeaveStatus.APPROVED && employee.getLeaveBalance() < leaveRequest.getNumberOfDays()) {
            throw new RuntimeException("Cannot approve leave. Insufficient leave balance.");
        }

        // Deduct leave balance if approved by manager
        if (newStatus == LeaveStatus.APPROVED) {
            employee.setLeaveBalance(employee.getLeaveBalance() - leaveRequest.getNumberOfDays());
            employeeRepository.save(employee);
        }

        return leaveRequestRepository.save(leaveRequest);
    }

}
