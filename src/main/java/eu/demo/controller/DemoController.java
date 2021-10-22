package eu.demo.controller;

import eu.demo.service.EmployeeService;
import eu.demo.service.model.EmployeeDm;
import eu.demo.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DemoController {

    private final EmployeeService employeeService;

    @GetMapping(Constants.REST_API_PREFIX + "/hello/{message}")
    public ResponseEntity<String> getHelloMessage(@PathVariable("message") String msg,
                                                  @RequestBody String body,
                                                  @RequestParam("query") String queryParam) {
        return ResponseEntity.ok(msg);
    }

    @GetMapping(Constants.REST_API_PREFIX + "/employees")
    public List<EmployeeDm> getEmployees() {
        return this.employeeService.findAllEmployees();
    }
}
