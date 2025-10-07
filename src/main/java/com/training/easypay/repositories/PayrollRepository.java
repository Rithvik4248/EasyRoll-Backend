package com.training.easypay.repositories;

import com.training.easypay.model.Employee;
import com.training.easypay.model.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayrollRepository extends JpaRepository<Payroll,Long> {
}
