package eu.demo.repository;

import eu.demo.repository.model.EmployeeEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.Currency;
import java.util.List;

@Repository
public class EmployeeMockRepository {

    public List<EmployeeEntity> findAll() {
        return List.of(
                employee(1L, "Smith", "Mary", "CEO", 10000.0, "EUR", 2020, Month.JANUARY),
                employee(2L, "Eason", "Bridgette", "CTO", 10000.0, "EUR", 2020, Month.JANUARY),
                employee(3L, "Jakeman", "Richmal", "Engineering Manager", 8000.0, "EUR", 2020, Month.FEBRUARY),
                employee(4L, "Toller", "Axel", "HR Manager", 8000.0, "EUR", 2020, Month.FEBRUARY),
                employee(5L, "Croft", "Leroi", "Operations Manager", 8000.0, "EUR", 2020, Month.MARCH),
                employee(6L, "Otis", "Malcolm", "Software Engineer", 6000.0, "EUR", 2021, Month.FEBRUARY),
                employee(7L, "Hooper", "Merle", "Recruiter", 6000.0, "EUR", 2021, Month.APRIL),
                employee(8L, "Howland", "Maurene", "Accountant", 6000.0, "EUR", 2021, Month.MARCH, 2021, Month.OCTOBER),
                employee(9L, "Rowe", "Kent", "Administrator", 6000.0, "EUR", 2021, Month.APRIL, 2021, Month.SEPTEMBER),
                employee(10L, "Whinery", "Anila", "Software Engineer", 6000.0, "EUR", 2021, Month.MARCH, 2021, Month.DECEMBER)
        );
    }

    private EmployeeEntity employee(long id, String lastname, String firstname, String jobTitle, double salaryAmount, String currencyCode, Integer hiringYear, Month hiringMonth) {
        return employee(id, lastname, firstname, jobTitle, salaryAmount, currencyCode, hiringYear, hiringMonth, null, null);
    }

    private EmployeeEntity employee(long id, String lastname, String firstname, String jobTitle, double salaryAmount, String currencyCode, Integer hiringYear, Month hiringMonth, Integer leavingYear, Month leavingMonth) {
        return EmployeeEntity.builder()
                .id(id)
                .lastname(lastname)
                .firstname(firstname)
                .jobTitle(jobTitle)
                .salaryAmount(salaryAmount)
                .salaryCurrency(Currency.getInstance(currencyCode))
                .hiringDate(getLocalDate(hiringYear, hiringMonth))
                .leavingDate(getLocalDate(leavingYear, leavingMonth))
                .build();
    }

    private LocalDate getLocalDate(Integer leavingYear, Month leavingMonth) {
        return (leavingYear != null && leavingMonth != null) ? LocalDate.of(leavingYear, leavingMonth, 1) : null;
    }
}
