package ro.siit.HRS.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.siit.HRS.dto.create.DepartmentCreateDto;
import ro.siit.HRS.dto.rturn.DepartmentReturnDto;
import ro.siit.HRS.model.Department;
import ro.siit.HRS.repository.DepartmentRepository;
import ro.siit.HRS.util.MapperUtil;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl {

    private final DepartmentRepository departmentRepository;
    private final MapperUtil mapperUtil;

    public DepartmentReturnDto createDepartment(DepartmentCreateDto departmentCreateDto) {

        Department department = new Department();
        department.setDepartmentName(departmentCreateDto.getDepartmentName());
        department.setManagerId(departmentCreateDto.getManagerId());
        department = departmentRepository.save(department);

        return mapperUtil.mapDepartment(department);
    }
}
