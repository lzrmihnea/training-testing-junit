package eu.demo.service.model;

import eu.demo.repository.model.EmployeeEntity;
import lombok.*;

import java.util.List;

@Data
@Builder
public class ParamTestDataDto {
    private List<EmployeeEntity> entities;
    private Double expectedAverageSalary;

}
