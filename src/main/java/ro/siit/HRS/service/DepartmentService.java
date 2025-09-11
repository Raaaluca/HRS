package ro.siit.HRS.service;

import ro.siit.HRS.dto.create.DepartmentCreateDto;
import ro.siit.HRS.dto.rturn.DepartmentReturnDto;

public interface DepartmentService {

    DepartmentReturnDto createDepartment(DepartmentCreateDto departmentCreateDto);
}
