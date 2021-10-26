package eu.demo.service;

import eu.demo.repository.EmployeeMockRepository;
import eu.demo.repository.model.EmployeeEntity;
import eu.demo.service.mapper.EmployeeMapper;
import eu.demo.service.model.EmployeeDm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceUnitSpyTest {
    @InjectMocks
    EmployeeService employeeService;
    @Mock
    EmployeeMockRepository employeeMockRepository;
    @Spy
    EmployeeMapper employeeMapper;

    @Test
    void findAll_expectedUnMockedMapperNotCalled() {
        //1. Given
        final long id = 101L;
        final String firstname = "Anna";
        final String lastname = "Smith";
        final EmployeeEntity employeeEntity = createUserEntity(id, firstname, lastname);
        when(employeeMockRepository.findAll()).thenReturn(List.of(employeeEntity));

        //2. When
        final List<EmployeeDm> actualList = this.employeeService.findAllEmployees();

        //3. Then
        verify(employeeMapper).toDm(any(EmployeeEntity.class));
        assertEquals(1, actualList.size());

        final EmployeeDm actualElement = actualList.get(0);
        assertNotNull(actualElement);
        assertEquals(id, actualElement.getId());
        assertEquals(firstname, actualElement.getFirstname());
        assertEquals(lastname, actualElement.getLastname());
    }

    private EmployeeEntity createUserEntity(long id, String firstname, String lastname) {
        return EmployeeEntity.builder()
                .id(id)
                .lastname(lastname)
                .firstname(firstname)
                .build();
    }
}