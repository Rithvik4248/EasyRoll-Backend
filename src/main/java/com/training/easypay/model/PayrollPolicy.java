package com.training.easypay.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payroll_policies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayrollPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String policyName;

    private double taxPercentage;

    private double hraPercentage;

    private double allowancesPercentage;

    private int paidLeaveDays;
}
