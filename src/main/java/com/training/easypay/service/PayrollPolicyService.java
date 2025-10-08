package com.training.easypay.service;

import com.training.easypay.model.PayrollPolicy;

public interface PayrollPolicyService {
    PayrollPolicy createPolicy(PayrollPolicy policy, Long adminId);
    PayrollPolicy updatePolicy(Long policyId, PayrollPolicy policyDetails, Long adminId);
    PayrollPolicy getPolicyByName(String policyName);
}
