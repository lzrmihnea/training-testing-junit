package eu.demo;

import eu.demo.controller.DemoController;
import eu.demo.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoApplication.class})
// Above we also add other configuration classes, whichever are relevant for the test
@Transactional
class IntegrationTest {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DemoController demoController;

    @Test
    void saveEmployee_expectedSuccessful() {
        // Given that we save an employee to the database
        // employeeService.saveEmployee(expectedEmployee);

        // When we retrieve the employee from the database via the controller
        // var retrievedEmployee = demoController.getEmployee(employeeId);

        // Then we expect to get the same employee
        // assertEquals(expectedEmployee, retrievedEmployee);
    }

}
