package ro.siit.HRS.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ro.siit.HRS.dto.create.DepartmentCreateDto;
import ro.siit.HRS.dto.rturn.DepartmentReturnDto;
import ro.siit.HRS.model.Department;
import ro.siit.HRS.repository.DepartmentRepository;
import ro.siit.HRS.service.impl.DepartmentServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;
    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @BeforeEach
    void setUp() {

    }

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

        DepartmentReturnDto resultedDepartmentReturnDto = departmentService.createDepartment(departmentCreateDto);

        assertEquals(expectedDepartmentReturnDto, resultedDepartmentReturnDto);
    }
}
