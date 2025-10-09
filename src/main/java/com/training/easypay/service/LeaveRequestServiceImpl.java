package com.training.easypay.service;

import com.training.easypay.Exceptions.EmployeeNotFoundException;
import com.training.easypay.Exceptions.LeaveNotFoundException;
import com.training.easypay.model.Employee;
import com.training.easypay.model.LeaveRequest;
import com.training.easypay.repositories.LeaveRequestRepository;
import org.antlr.v4.runtime.LexerNoViableAltException;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService{
    @Autowired
    LeaveRequestRepository leaveRepo;

    @Override
    public LeaveRequest save(LeaveRequest leave) {
        return leaveRepo.save(leave);
    }

    @Override
    public List<LeaveRequest> findAll() {
        return leaveRepo.findAll();
    }

    @Override
    public LeaveRequest findById(Long id) {
        return leaveRepo.findById(id).orElseThrow(()->new LeaveNotFoundException("Request leave does not exist."));
    }

    @Override
    public List<LeaveRequest> getPendingLeaveRequests() {
        return leaveRepo.findByStatus("PENDING");
    }

}
