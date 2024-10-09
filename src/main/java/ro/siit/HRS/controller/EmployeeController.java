package ro.siit.HRS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.siit.HRS.dto.EmployeeCreateDto;
import ro.siit.HRS.dto.EmployeeReturnDto;
import ro.siit.HRS.dto.ManagerCreateDto;
import ro.siit.HRS.model.Employee;
import ro.siit.HRS.service.EmployeeService;

@RequestMapping(path = "/employees")
@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping(path = "/id")
    public EmployeeReturnDto getEmployeeById(@RequestParam Long id) {

        return employeeService.findById(id);
    }

    @GetMapping(path = "/create")
    public Employee createEmployee(@RequestBody EmployeeCreateDto employeeCreateDto) {

        return employeeService.createEmployee(employeeCreateDto);
    }
}
