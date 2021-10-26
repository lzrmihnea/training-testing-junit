package eu.demo.service;

import eu.demo.repository.EmployeeMockRepository;
import eu.demo.repository.model.EmployeeEntity;
import eu.demo.service.mapper.EmployeeMapper;
import eu.demo.service.model.EmployeeDm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceUnitMockTest {
    @InjectMocks
    EmployeeService employeeService;
    @Mock
    EmployeeMockRepository employeeMockRepository;
    @Mock
    EmployeeMapper employeeMapper;


    @Test
    void findAll_expectedEmptyListRetrieved() {
        //1. Given

        //2. When
        this.employeeService.findAllEmployees();

        //3. Then
        verify(employeeMockRepository).findAll();
        verifyNoInteractions(employeeMapper);
    }

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
        assertNull(actualList.get(0));
    }

    // ... continued ...

    @Test
    void findAll_expectedListRetrievedMapperCalled() {
        //1. Given
        final long id = 101L;
        final String firstname = "Anna";
        final String lastname = "Smith";
        final EmployeeEntity employeeEntity = createUserEntity(id, firstname, lastname);
        when(employeeMockRepository.findAll()).thenReturn(List.of(employeeEntity));
        when(employeeMapper.toDm(employeeEntity)).thenCallRealMethod();

        //2. When
        final List<EmployeeDm> actualList = this.employeeService.findAllEmployees();

        //3. Then
        verify(employeeMockRepository, atMostOnce()).findAll();
        verify(employeeMapper, atMostOnce()).toDm(any(EmployeeEntity.class));
        assertEquals(1, actualList.size());
        final EmployeeDm actualReturned = actualList.get(0);
        assertEquals(id, actualReturned.getId());
        assertEquals(firstname, actualReturned.getFirstname());
        assertEquals(lastname, actualReturned.getLastname());
    }

    private EmployeeEntity createUserEntity(long id, String firstname, String lastname) {
        return EmployeeEntity.builder()
                .id(id)
                .lastname(lastname)
                .firstname(firstname)
                .build();
    }
}