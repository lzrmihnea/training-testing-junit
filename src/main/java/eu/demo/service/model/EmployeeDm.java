package eu.demo.service.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Currency;

@Data
@Builder
public class EmployeeDm {
    private Long id;
    private String firstname;
    private String lastname;
    private String jobTitle;
    private Double salaryAmount;
    private Currency salaryCurrency;
    private LocalDate hiringDate;
    private LocalDate leavingDate;
}
