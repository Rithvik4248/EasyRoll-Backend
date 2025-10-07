package com.training.easypay.repositories;

import com.training.easypay.model.PayrollData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayrollDataRepository extends JpaRepository<PayrollData, Long> {
    PayrollData findByEmployeeId(Long employeeId);
}
