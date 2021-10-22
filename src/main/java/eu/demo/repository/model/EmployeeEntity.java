package eu.demo.repository.model;

import lombok.*;

import java.time.LocalDate;
import java.util.Currency;

@Data
@Builder
public class EmployeeEntity {
    private Long id;
    private String firstname;
    private String lastname;
    private String jobTitle;
    private Double salaryAmount;
    private Currency salaryCurrency;
    private LocalDate hiringDate;
    private LocalDate leavingDate;
}
