package eu.demo.service.mapper;

import eu.demo.repository.model.EmployeeEntity;
import eu.demo.service.model.EmployeeDm;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public EmployeeDm toDm(EmployeeEntity entity) {
        return EmployeeDm.builder()
                .id(entity.getId())
                .lastname(entity.getLastname())
                .firstname(entity.getFirstname())
                .jobTitle(entity.getJobTitle())
                .salaryAmount(entity.getSalaryAmount())
                .salaryCurrency(entity.getSalaryCurrency())
                .hiringDate(entity.getHiringDate())
                .leavingDate(entity.getLeavingDate())
                .build();
    }
}
