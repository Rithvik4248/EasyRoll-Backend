package com.training.easypay.dtos;

import com.training.easypay.model.EmployeeStatus;
import lombok.Data;

@Data
public class EmployeeDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String designation;
    private Double salary;
    private EmployeeStatus status;
}
