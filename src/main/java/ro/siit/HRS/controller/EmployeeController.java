package ro.siit.HRS.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.siit.HRS.dto.create.EmployeeCreateDto;
import ro.siit.HRS.dto.response.EmployeeReturnDto;
import ro.siit.HRS.dto.update.EmployeeUpdateDto;
import ro.siit.HRS.service.EmployeeService;

@RequestMapping(path = "/employees")
@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping(path = "/id")
    public EmployeeReturnDto getEmployeeById(@RequestParam Long id) {

        return employeeService.findById(id);
    }

    @PostMapping(path = "/create")
    public EmployeeReturnDto createEmployee(@RequestBody @Valid EmployeeCreateDto employeeCreateDto) {

        return employeeService.createEmployee(employeeCreateDto);
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<Void> deleteEmployee(@RequestParam Long employeeId) {

        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/update")
    public EmployeeReturnDto update(@RequestBody EmployeeUpdateDto employeeUpdateDto) {

        return employeeService.updateEmployee(employeeUpdateDto);
    }
}