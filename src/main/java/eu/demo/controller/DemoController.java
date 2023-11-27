package eu.demo.controller;

import eu.demo.service.EmployeeService;
import eu.demo.service.model.EmployeeDm;
import eu.demo.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DemoController {

    private final EmployeeService employeeService;

    @GetMapping(Constants.REST_API_PREFIX + "/hello/{message}")
    public ResponseEntity<String> getHelloMessage(@PathVariable("message") String msg,
                                                  @RequestParam(value = "query", required = false) String queryParam) {
        return ResponseEntity.ok(msg);
    }

    @GetMapping(Constants.REST_API_PREFIX + "/employees")
    public List<EmployeeDm> getEmployees() {
        return this.employeeService.findAllEmployees();
    }
}
