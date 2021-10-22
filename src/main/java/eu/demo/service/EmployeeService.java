package eu.demo.service;

import eu.demo.repository.EmployeeMockRepository;
import eu.demo.service.mapper.EmployeeMapper;
import eu.demo.service.model.EmployeeDm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
}
