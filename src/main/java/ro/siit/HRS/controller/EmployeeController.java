package ro.siit.HRS.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.siit.HRS.dto.create.EmployeeCreateDto;
import ro.siit.HRS.dto.rturn.EmployeeReturnDto;
import ro.siit.HRS.dto.update.EmployeeUpdateDto;
import ro.siit.HRS.service.impl.EmployeeServiceImpl;

@RequestMapping(path = "/employees")
@RestController
public class EmployeeController {
    @Autowired
    private EmployeeServiceImpl employeeService;

    @GetMapping(path = "/id")
    public EmployeeReturnDto getEmployeeById(@RequestParam Long id) {

        return employeeService.findById(id);
    }

    @PostMapping(path = "/create")
    public EmployeeReturnDto createEmployee(@RequestBody @Valid EmployeeCreateDto employeeCreateDto) {

        return employeeService.createEmployee(employeeCreateDto);
    }

    @DeleteMapping(path = "/delete")
    public String deleteEmployee(@RequestParam Long employeeId){

        return employeeService.deleteEmployee(employeeId);
    }

    @PutMapping(path = "/update")
    public EmployeeReturnDto update(@RequestBody EmployeeUpdateDto employeeUpdateDto) {

        return employeeService.updateEmployee(employeeUpdateDto);
    }
}