package ro.siit.HRS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.siit.HRS.model.Employee;
import ro.siit.HRS.service.EmployeeService;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping(path = "/employees/id")
    public Employee getEmployeeById(@RequestParam Long id) {

        return employeeService.findById(id);
    }
}
