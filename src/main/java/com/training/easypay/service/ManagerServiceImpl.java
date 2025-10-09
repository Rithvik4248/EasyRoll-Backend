package com.training.easypay.service;

import com.training.easypay.Exceptions.LeaveNotFoundException;
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

    @Autowired
    private LeaveRequestServiceImpl LeaveRequestServiceImpl;


    @Override
    public List<PayrollData> getGroupedAndSortedPayrollData() {
        Sort sort = Sort.by(Sort.Order.asc("employee.id"), Sort.Order.desc("payroll.id"));
        List<PayrollData> payrollDataList = payrollDataRepository.findAll(sort);
        return payrollDataList;
//        return payrollDataList.stream()
//                .collect(Collectors.groupingBy(pd -> pd.getEmployee().getId()));
    }
   // this function is dependent on employee class please read this comment
    @Override
    public LeaveRequest updateLeaveRequestStatus(Long leaveRequestId, String status) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new LeaveNotFoundException("Leave request not found"));

        LeaveStatus newStatus = LeaveStatus.valueOf(status.toUpperCase());
        leaveRequest.setStatus(newStatus);
        leaveRequestRepository.save(leaveRequest);
        return leaveRequest;
    }

}
