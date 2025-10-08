package com.training.easypay.service;

import com.training.easypay.model.Admin;
import com.training.easypay.model.PayrollPolicy;
import com.training.easypay.repositories.AdminRepository;
import com.training.easypay.repositories.PayrollPolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayrollPolicyServiceImpl implements PayrollPolicyService {

    @Autowired
    private PayrollPolicyRepository payrollPolicyRepository;

    @Autowired
    private AdminRepository adminRepository;

    private void checkPermissions(Long adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        if (!admin.getDesignation().equals("HR_MANAGER")) {
            throw new RuntimeException("You do not have permission to perform this action.");
        }
    }

    @Override
    public PayrollPolicy createPolicy(PayrollPolicy policy, Long adminId) {
        checkPermissions(adminId);
        return payrollPolicyRepository.save(policy);
    }

    @Override
    public PayrollPolicy updatePolicy(Long policyId, PayrollPolicy policyDetails, Long adminId) {
        checkPermissions(adminId);
        PayrollPolicy policy = payrollPolicyRepository.findById(policyId)
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        policy.setPolicyName(policyDetails.getPolicyName());
        policy.setPaidLeaveDays(policyDetails.getPaidLeaveDays());
        policy.setTaxPercentage(policyDetails.getTaxPercentage());
        policy.setBonusPercentage(policyDetails.getBonusPercentage());

        return payrollPolicyRepository.save(policy);
    }

    @Override
    public PayrollPolicy getPolicyByName(String policyName) {
        return payrollPolicyRepository.findByPolicyName(policyName);
    }
}
