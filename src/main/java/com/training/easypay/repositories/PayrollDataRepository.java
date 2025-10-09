package com.training.easypay.repositories;

import com.training.easypay.model.PayrollData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayrollDataRepository extends JpaRepository<PayrollData, Long> {
    List<PayrollData> findByEmployeeId(Long employeeId);
}