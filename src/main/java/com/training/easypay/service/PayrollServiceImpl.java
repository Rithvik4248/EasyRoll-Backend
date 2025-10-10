package com.training.easypay.service;

import com.training.easypay.exceptions.ResourceNotFoundException;
import com.training.easypay.model.Employee;
import com.training.easypay.model.Payroll;
import com.training.easypay.model.PayrollPolicy;
import com.training.easypay.repositories.EmployeeRepository;
import com.training.easypay.repositories.PayrollRepository;
import com.training.easypay.repositories.PayrollPolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PayrollServiceImpl implements PayrollService {

    private final EmployeeRepository employeeRepository;
    private final PayrollRepository payrollRepository;
    private final PayrollPolicyRepository payrollPolicyRepository;

    @Autowired
    public PayrollServiceImpl(EmployeeRepository employeeRepository, PayrollRepository payrollRepository, PayrollPolicyRepository payrollPolicyRepository) {
        this.employeeRepository = employeeRepository;
        this.payrollRepository = payrollRepository;
        this.payrollPolicyRepository = payrollPolicyRepository;
    }

    @Override
    public List<Payroll> calculatePayrollForPeriod(String payPeriod) {
        // Assume there is one global payroll policy. Fetch the first one found.
        PayrollPolicy policy = payrollPolicyRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No Payroll Policy has been defined. Please create a policy first."));

        List<Employee> employees = employeeRepository.findAll();
        List<Payroll> payrolls = new ArrayList<>();

        for (Employee employee : employees) {
            double basicSalary = employee.getSalary();
            double hra = basicSalary * (policy.getHraPercentage() / 100);
            double allowances = basicSalary * (policy.getAllowancesPercentage() / 100);
            double taxDeduction = basicSalary * (policy.getTaxPercentage() / 100);

            double netPay = basicSalary + hra + allowances - taxDeduction;

            Payroll payroll = new Payroll();
            payroll.setEmployeeId(employee.getId());
            payroll.setBasicSalary(basicSalary);
            payroll.setHra(hra);
            payroll.setAllowances(allowances);
            payroll.setDeductions(taxDeduction);
            payroll.setNetPay(netPay);
            payroll.setPayPeriod(payPeriod);
            payroll.setPaymentDate(LocalDate.now()); // Or a specific date for the period

            payrolls.add(payrollRepository.save(payroll));
        }

        return payrolls;
    }

    @Override
    public void processPaymentsForPeriod(String payPeriod) {
        // This is a placeholder for a more complex payment processing logic.
        // In a real system, this would integrate with a payment gateway or accounting system.
        List<Payroll> payrollsToProcess = payrollRepository.findByPayPeriod(payPeriod);
        System.out.println("Processing payments for " + payrollsToProcess.size() + " employees for the period: " + payPeriod);
        // Here, you would change the status of payrolls to 'PAID', for example.
    }
}
