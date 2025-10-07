package com.training.easypay.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payroll")
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;

    private Double basicSalary;
    private Double hra;
    private Double allowances;
    private Double deductions;
    private Double netPay;

    private String payPeriod;  // e.g. "Sept-2025"
    private LocalDate paymentDate;

    // Constructors, getters, setters
}

