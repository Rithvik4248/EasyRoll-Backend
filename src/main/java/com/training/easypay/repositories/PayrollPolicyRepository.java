package com.training.easypay.repositories;

import com.training.easypay.model.PayrollPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayrollPolicyRepository extends JpaRepository<PayrollPolicy, Long> {
}
