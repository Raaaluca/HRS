package ro.siit.HRS.service;

import org.hamcrest.Matchers;
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
import ro.siit.HRS.model.Manager;
import ro.siit.HRS.repository.DepartmentRepository;
import ro.siit.HRS.repository.EmployeeRepository;
import ro.siit.HRS.repository.ManagerRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;
    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void createDepartment() {

              //build the parameter(s) needed
        DepartmentCreateDto departmentCreateDto = new DepartmentCreateDto();
        departmentCreateDto.setManagerId(1L);
        departmentCreateDto.setDepartmentName("Marketing");

              //build the expected result from calling the service method in test
        DepartmentReturnDto expectedDepartmentReturnDto = new DepartmentReturnDto();
        expectedDepartmentReturnDto.setDepartmentName("Marketing");
        expectedDepartmentReturnDto.setManagerId(1L);

            //build what a result should look like from first repository call, leaveRequestRepository.save
        Department department = new Department();
        department.setDepartmentName("Marketing");
        department.setManagerId(1L);

          //mock (simulate) the repository calls
        Mockito.when(departmentRepository.save(any())).thenReturn(department);

          //make the actual call of the service method in test
        DepartmentReturnDto resultedDepartmentReturnDto = departmentService.createDepartment(departmentCreateDto);

        //verify the result against what was expected
        assertEquals(expectedDepartmentReturnDto, resultedDepartmentReturnDto);

    }
}
