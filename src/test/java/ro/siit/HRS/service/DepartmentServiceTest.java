package ro.siit.HRS.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ro.siit.HRS.dto.create.DepartmentCreateDto;
import ro.siit.HRS.dto.response.DepartmentReturnDto;
import ro.siit.HRS.model.Department;
import ro.siit.HRS.repository.DepartmentRepository;
import ro.siit.HRS.service.impl.DepartmentServiceImpl;
import ro.siit.HRS.util.MapperUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Spy
    private DepartmentRepository departmentRepository;
    @Mock
    private MapperUtil mapperUtil;
    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Test
    void createDepartment() {

        DepartmentCreateDto departmentCreateDto = new DepartmentCreateDto();
        departmentCreateDto.setManagerId(1L);
        departmentCreateDto.setDepartmentName("Marketing");

        DepartmentReturnDto expectedDepartmentReturnDto = new DepartmentReturnDto();
        expectedDepartmentReturnDto.setDepartmentName("Marketing");
        expectedDepartmentReturnDto.setManagerId(1L);

        Department department = new Department();
        department.setDepartmentName("Marketing");
        department.setManagerId(1L);

        Mockito.when(departmentRepository.save(any())).thenReturn(department);
        Mockito.when(mapperUtil.mapDepartment(any())).thenReturn(expectedDepartmentReturnDto);

        DepartmentReturnDto resultedDepartmentReturnDto = departmentService.createDepartment(departmentCreateDto);

        assertEquals(expectedDepartmentReturnDto, resultedDepartmentReturnDto);
    }
}
