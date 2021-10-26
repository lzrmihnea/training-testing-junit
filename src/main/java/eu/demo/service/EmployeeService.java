package eu.demo.service;

import eu.demo.repository.EmployeeMockRepository;
import eu.demo.repository.model.EmployeeEntity;
import eu.demo.service.mapper.EmployeeMapper;
import eu.demo.service.model.EmployeeDm;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeMockRepository employeeMockRepository;
    private final EmployeeMapper employeeMapper;

    public List<EmployeeDm> findAllEmployees() {
        return employeeMockRepository.findAll().stream()
                .map(employeeMapper::toDm)
                .collect(Collectors.toList());
    }

    public Double getAverageSalary() {
        final List<EmployeeEntity> allEntities = employeeMockRepository.findAll();
        if (CollectionUtils.isEmpty(allEntities))
            return 0.0;
        final long employeeCount = allEntities.size();
        final double salarySum = allEntities
                .stream()
                .map(employeeMapper::toDm)
                .mapToDouble(EmployeeDm::getSalaryAmount)
                .sum();
        return (salarySum / employeeCount);
    }
}
