package com.training.easypay.repositories;

import com.training.easypay.model.PayrollPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayrollPolicyRepository extends JpaRepository<PayrollPolicy, Long> {
    PayrollPolicy findByPolicyName(String policyName);
}
