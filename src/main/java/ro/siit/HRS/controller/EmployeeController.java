package ro.siit.HRS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.siit.HRS.dto.*;
import ro.siit.HRS.model.Employee;
import ro.siit.HRS.service.EmployeeService;
import ro.siit.HRS.service.LeaveRequestService;

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
    public EmployeeReturnDto createEmployee(@RequestBody EmployeeCreateDto employeeCreateDto) {

        return employeeService.createEmployee(employeeCreateDto);
    }
    @DeleteMapping(path = "/delete")
    public String deleteEmployee(@RequestParam Long employeeId){

        return employeeService.deleteEmployee(employeeId);
    }

}