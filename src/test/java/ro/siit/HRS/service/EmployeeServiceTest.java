package ro.siit.HRS.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.siit.HRS.dto.create.EmployeeCreateDto;
import ro.siit.HRS.dto.rturn.EmployeeReturnDto;
import ro.siit.HRS.model.Department;
import ro.siit.HRS.model.Employee;
import ro.siit.HRS.model.Manager;
import ro.siit.HRS.repository.DepartmentRepository;
import ro.siit.HRS.repository.EmployeeRepository;
import ro.siit.HRS.repository.ManagerRepository;
import ro.siit.HRS.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private ManagerRepository managerRepository;
    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void createEmployee() {

        EmployeeCreateDto employeeCreateDto = new EmployeeCreateDto();
        employeeCreateDto.setAddress("Str. Infratirii");
        employeeCreateDto.setName("Daniel Roibu");
        employeeCreateDto.setCity("Timisoara");
        employeeCreateDto.setGender("male");
        employeeCreateDto.setEmail("daniel_r@yahoo.com");
        employeeCreateDto.setJobTitle("Payroll Admin");
        employeeCreateDto.setNationalId("773389");
        employeeCreateDto.setPhoneNumber("0733125775");
        employeeCreateDto.setStartDate(LocalDate.of(2023, 11, 11));
        employeeCreateDto.setEndDate(LocalDate.of(2026, 9, 4));
        employeeCreateDto.setSuperiorId(1L);

        EmployeeReturnDto expectedEmployeeReturnDto = new EmployeeReturnDto();
        expectedEmployeeReturnDto.setId(1L);
        expectedEmployeeReturnDto.setName("Daniel Roibu");
        expectedEmployeeReturnDto.setPhoneNumber("0733125775");
        expectedEmployeeReturnDto.setEmail("daniel_r@yahoo.com");
        expectedEmployeeReturnDto.setSuperiorId(1L);
        expectedEmployeeReturnDto.setAddress("Str. Infratirii");
        expectedEmployeeReturnDto.setCity("Timisoara");
        expectedEmployeeReturnDto.setGender("male");
        expectedEmployeeReturnDto.setStartDate(LocalDate.of(2023,11,11));
        expectedEmployeeReturnDto.setEndDate(LocalDate.of(2026,9,4));
        expectedEmployeeReturnDto.setJobTitle("Payroll Admin");
        expectedEmployeeReturnDto.setAnnualLeaveDays(21);

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setSuperiorId(1L);
        employee.setGender("male");
        employee.setCity("Timisoara");
        employee.setEmail("daniel_r@yahoo.com");
        employee.setAddress("Str. Infratirii");
        employee.setStartDate(LocalDate.of(2023,11,11));
        employee.setEndDate(LocalDate.of(2026, 9, 4));
        employee.setLeaveRequests(new ArrayList<>());
        employee.setName("Daniel Roibu");
        employee.setNationalId("773389");
        employee.setPhoneNumber("0733125775");
        employee.setJobTitle("Payroll Admin");
        employee.setAnnualLeaveDays(21);

        Manager manager = new Manager();
        manager.setId(1L);
        manager.setEmployees(new ArrayList<>());

        Department department = new Department();
        department.setDepartmentName("HR");

        Mockito.when(employeeRepository.save(any())).thenReturn(employee);
        Mockito.when(managerRepository.findById((any()))).thenReturn(Optional.of(manager));
        Mockito.when(departmentRepository.findByDepartmentName((any()))).thenReturn(department);

        EmployeeReturnDto resultedEmployeeReturnDto = employeeService.createEmployee(employeeCreateDto);
        assertEquals(expectedEmployeeReturnDto, resultedEmployeeReturnDto);
    }

    @Test
    void updateEmployee() {

    }

    @Test
    void getSuperiorIdByJobTitle() {

    }

    @Test
    void deleteEmployee() {

    }

    @Test
    void getAuthenticationDetails() {

    }

    @Test
    void getUpdatePersonalDetails() {

    }

    @Test
    void getEmployeeRemainingDays() {

    }

    @Test
    void updateEmployeeDto() {

    }
}