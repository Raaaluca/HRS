package ro.siit.HRS.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.siit.HRS.dto.create.DepartmentCreateDto;
import ro.siit.HRS.dto.response.DepartmentReturnDto;
import ro.siit.HRS.service.DepartmentService;

@RequestMapping(path = "/departments")
@RestController
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentReturnDto> createDepartment(@RequestBody @Valid DepartmentCreateDto departmentCreateDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(departmentService.createDepartment(departmentCreateDto));
    }
}

