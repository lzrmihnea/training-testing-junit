package eu.demo.service;

import eu.demo.repository.EmployeeMockRepository;
import eu.demo.repository.model.EmployeeEntity;
import eu.demo.service.mapper.EmployeeMapper;
import eu.demo.service.model.ParamTestDataDto;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Month;
import java.util.List;
import java.util.stream.Stream;

import static eu.demo.service.EmployeeTestUtils.employee;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceUnitParameterizedTest {
    @InjectMocks
    EmployeeService employeeService;
    @Mock
    EmployeeMockRepository employeeMockRepository;
    @Spy
    EmployeeMapper employeeMapper;

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("eu.demo.service.EmployeeServiceUnitParameterizedTest#inputData")
    void findAll_expectedListRetrievedMapperCalled(ParamTestDataDto inputDto) {
        // 1. Given
        final Double expectedAverageSalary = inputDto.getExpectedAverageSalary();
        final List<EmployeeEntity> mockedEntities = inputDto.getEntities();
        when(employeeMockRepository.findAll()).thenReturn(mockedEntities);

        // 2. When
        final Double actualAverageSalary = this.employeeService.getAverageSalary();

        // 3. Then
        assertEquals(expectedAverageSalary, actualAverageSalary);
    }

    private static Stream<ParamTestDataDto> inputData() {
        final ParamTestDataDto testCase1 = ParamTestDataDto.builder()
                .entities(List.of(
                        employee(1L, "Smith", "Mary", "CEO", 9_500.0, "EUR", 2020, Month.JANUARY),
                        employee(2L, "Eason", "Bridgette", "CTO", 9_500.0, "EUR", 2020, Month.JANUARY),
                        employee(3L, "Jakeman", "Richmal", "Engineering Manager", 8_000.0, "EUR", 2020, Month.FEBRUARY)
                ))
                .expectedAverageSalary(9_000.0)
                .build();

        final ParamTestDataDto testCase2 = ParamTestDataDto.builder()
                .entities(List.of(
                        employee(4L, "Toller", "Axel", "HR Manager", 8_000.0, "EUR", 2020, Month.FEBRUARY),
                        employee(5L, "Croft", "Leroi", "Operations Manager", 8_000.0, "EUR", 2020, Month.MARCH),
                        employee(6L, "Otis", "Malcolm", "Software Engineer", 6_000.0, "EUR", 2021, Month.FEBRUARY),
                        employee(7L, "Hooper", "Merle", "Recruiter", 5_000.0, "EUR", 2021, Month.APRIL)
                ))
                .expectedAverageSalary(6_750.0)
                .build();

        final ParamTestDataDto testCase3 = ParamTestDataDto.builder()
                .entities(List.of(
                        employee(8L, "Howland", "Maurene", "Accountant", 5_000.0, "EUR", 2021, Month.MARCH, 2021, Month.OCTOBER),
                        employee(9L, "Rowe", "Kent", "Administrator", 4_000.0, "EUR", 2021, Month.APRIL, 2021, Month.SEPTEMBER),
                        employee(10L, "Whinery", "Anila", "Software Engineer", 3_000.0, "EUR", 2021, Month.MARCH, 2021, Month.DECEMBER)
                ))
                .expectedAverageSalary(4_000.0)
                .build();

        return Stream.of(testCase1, testCase2, testCase3);
    }
}