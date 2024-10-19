package ro.siit.HRS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.siit.HRS.dto.DepartmentCreateDto;
import ro.siit.HRS.dto.DepartmentReturnDto;
import ro.siit.HRS.service.DepartmentService;

@RequestMapping(path = "/departments")
@RestController
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping(path = "/create")
    public DepartmentReturnDto createDepartment(@RequestBody DepartmentCreateDto departmentCreateDto) {

        return departmentService.createDepartment(departmentCreateDto);
    }
}

