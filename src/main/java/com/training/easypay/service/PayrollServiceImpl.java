package com.training.easypay.service;

import com.training.easypay.model.Employee;
import com.training.easypay.model.LeaveRequest;
import com.training.easypay.model.Payroll;
import com.training.easypay.model.PayrollData;
import com.training.easypay.repositories.EmployeeRepository;
import com.training.easypay.repositories.LeaveRequestRepository;
import com.training.easypay.repositories.PayrollDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PayrollServiceImpl implements PayrollService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private PayrollDataRepository payrollDataRepository;

    @Override
    public Payroll calculatePayroll(Long employeeId, int month, int year) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        List<LeaveRequest> leaveRequests = leaveRequestRepository.findByEmployeeId(employeeId);

        List<LeaveRequest> monthlyLeaveRequests = leaveRequests.stream()
                .filter(lr -> lr.getStartDate().getMonthValue() == month && lr.getStartDate().getYear() == year)
                .collect(Collectors.toList());

        int leaveDays = monthlyLeaveRequests.stream()
                .mapToInt(lr -> lr.getEndDate().getDayOfMonth() - lr.getStartDate().getDayOfMonth() + 1)
                .sum();

        double monthlySalary = employee.getSalary();
        double perDaySalary = monthlySalary / 30;
        double deduction = perDaySalary * leaveDays;
        double netSalary = monthlySalary - deduction;

        Payroll payroll = new Payroll();
        payroll.setBasicSalary(monthlySalary);
        payroll.setDeductions(deduction);
        payroll.setNetPay(netSalary);
        payroll.setPayPeriod(YearMonth.of(year, month).toString());
        payroll.setPaymentDate(LocalDate.now());

        PayrollData payrollData = new PayrollData();
        payrollData.setEmployee(employee);
        payrollData.setPayroll(payroll);

        PayrollData savedPayrollData = payrollDataRepository.save(payrollData);

        return savedPayrollData.getPayroll();
    }
}
