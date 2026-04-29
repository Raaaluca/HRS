package ro.siit.HRS.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeReturnDto> getEmployeeById(@PathVariable Long employeeId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeService.findById(employeeId));
    }

    @PostMapping
    public ResponseEntity<EmployeeReturnDto> createEmployee(@RequestBody @Valid EmployeeCreateDto employeeCreateDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.createEmployee(employeeCreateDto));
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeId) {

        employeeService.deleteEmployee(employeeId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping
    public ResponseEntity<EmployeeReturnDto> updateEmployee(@RequestBody @Valid EmployeeUpdateDto employeeUpdateDto) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeService.updateEmployee(employeeUpdateDto));
    }
}