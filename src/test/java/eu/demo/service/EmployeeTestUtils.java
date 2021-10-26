package eu.demo.service;

import eu.demo.repository.model.EmployeeEntity;

import java.time.LocalDate;
import java.time.Month;
import java.util.Currency;

public class EmployeeTestUtils {
    static EmployeeEntity employee(long id, String lastname, String firstname, String jobTitle, double salaryAmount, String currencyCode, Integer hiringYear, Month hiringMonth) {
        return employee(id, lastname, firstname, jobTitle, salaryAmount, currencyCode, hiringYear, hiringMonth, null, null);
    }

    static EmployeeEntity employee(long id, String lastname, String firstname, String jobTitle, double salaryAmount, String currencyCode, Integer hiringYear, Month hiringMonth, Integer leavingYear, Month leavingMonth) {
        return EmployeeEntity.builder()
                .id(id)
                .lastname(lastname)
                .firstname(firstname)
                .jobTitle(jobTitle)
                .salaryAmount(salaryAmount)
                .salaryCurrency(Currency.getInstance(currencyCode))
                .hiringDate(getLocalDate(hiringYear, hiringMonth))
                .leavingDate(getLocalDate(leavingYear, leavingMonth)).build();
    }

    static LocalDate getLocalDate(Integer leavingYear, Month leavingMonth) {
        return (leavingYear != null && leavingMonth != null) ? LocalDate.of(leavingYear, leavingMonth, 1) : null;
    }
}
