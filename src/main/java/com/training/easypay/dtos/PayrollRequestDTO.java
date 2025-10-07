package com.training.easypay.dtos;

import lombok.Data;

@Data
public class PayrollRequestDTO {
    private Long employeeId;
    private int month;
    private int year;
}
