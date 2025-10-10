package com.training.easypay.repositories;

import com.training.easypay.model.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayrollRepository extends JpaRepository<Payroll,Long> {
    List<Payroll> findByPayPeriod(String payPeriod);
}
