package com.training.easypay.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PayrollDTO {
    private Long id;
    private Double basicSalary;
    private Double hra;
    private Double allowances;
    private Double deductions;
    private Double netPay;
    private String payPeriod;
    private LocalDate paymentDate;
}
