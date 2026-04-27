package ro.siit.HRS.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.siit.HRS.dto.create.DepartmentCreateDto;
import ro.siit.HRS.dto.response.DepartmentReturnDto;
import ro.siit.HRS.model.Department;
import ro.siit.HRS.repository.DepartmentRepository;
import ro.siit.HRS.service.DepartmentService;
import ro.siit.HRS.util.MapperUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final MapperUtil mapperUtil;

    public DepartmentReturnDto createDepartment(DepartmentCreateDto departmentCreateDto) {

        log.info("Preparing to create department from {}", departmentCreateDto);
        Department department = new Department();
        department.setDepartmentName(departmentCreateDto.getDepartmentName());
        department.setManagerId(departmentCreateDto.getManagerId());
        department = departmentRepository.save(department);
        log.info("Department created successfully with id: {}", department.getId());
        return mapperUtil.mapDepartment(department);
    }
}
