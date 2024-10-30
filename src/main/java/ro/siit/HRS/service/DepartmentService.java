package ro.siit.HRS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.siit.HRS.dto.create.DepartmentCreateDto;
import ro.siit.HRS.dto.rturn.DepartmentReturnDto;
import ro.siit.HRS.model.Department;
import ro.siit.HRS.repository.DepartmentRepository;

import java.util.ArrayList;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public Department findById(Long id) {

        return departmentRepository.findById(id)
                .orElseThrow();
    }

    public DepartmentReturnDto mapDepartment(Department department) {

        DepartmentReturnDto departmentReturnDto = new DepartmentReturnDto();
        departmentReturnDto.setDepartmentName(department.getDepartmentName());
        departmentReturnDto.setManagerId(department.getManagerId());

        return departmentReturnDto;
    }

    public DepartmentReturnDto createDepartment(DepartmentCreateDto departmentCreateDto) {

        Department department = new Department();
        department.setDepartmentName(departmentCreateDto.getDepartmentName());
        department.setManagerId(departmentCreateDto.getManagerId());
        department = departmentRepository.save(department);

        return mapDepartment(department);

    }
}
