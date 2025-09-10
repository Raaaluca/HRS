package ro.siit.HRS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.siit.HRS.dto.create.DepartmentCreateDto;
import ro.siit.HRS.dto.rturn.DepartmentReturnDto;
import ro.siit.HRS.service.impl.DepartmentServiceImpl;

@RequestMapping(path = "/departments")
@RestController
public class DepartmentController {
    @Autowired
    private DepartmentServiceImpl departmentService;

    @PostMapping(path = "/create")
    public DepartmentReturnDto createDepartment(@RequestBody DepartmentCreateDto departmentCreateDto) {

        return departmentService.createDepartment(departmentCreateDto);
    }
}

