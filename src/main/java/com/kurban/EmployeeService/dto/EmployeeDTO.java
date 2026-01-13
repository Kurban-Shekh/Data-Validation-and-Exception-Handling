package com.kurban.EmployeeService.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    @NotBlank(message = "first name can't be null")
    @Size(max = 30)
    private String firstName;

    @NotNull(message = "last name can't be null.")
    private String lastName;

    @NotBlank(message = "Department name must be provided")
    private String department;

    @Range(min = 0, message = "salary can't be negative")
    private BigDecimal salary;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^\\d{10}$",message = "Invalid phone number")
    private String phone;

    @NotBlank
    @Size(max = 50, message = "Provide the correct location")
    private String location;
}
