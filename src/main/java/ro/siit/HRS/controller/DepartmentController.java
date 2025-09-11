package ro.siit.HRS.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.siit.HRS.dto.create.DepartmentCreateDto;
import ro.siit.HRS.dto.rturn.DepartmentReturnDto;
import ro.siit.HRS.service.DepartmentService;

@RequestMapping(path = "/departments")
@RestController
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping(path = "/create")
    public DepartmentReturnDto createDepartment(@RequestBody DepartmentCreateDto departmentCreateDto) {

        return departmentService.createDepartment(departmentCreateDto);
    }
}

