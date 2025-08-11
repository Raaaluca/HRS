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
import ro.siit.HRS.dto.update.EmployeeUpdateDto;
import ro.siit.HRS.model.Department;
import ro.siit.HRS.model.Employee;
import ro.siit.HRS.model.Manager;
import ro.siit.HRS.model.User;
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

    @InjectMocks
    private LeaveRequestService leaveRequestService;
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

        //initial data
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

        //expected result after method call
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

        //actual result after method call
        EmployeeReturnDto resultedEmployeeReturnDto = employeeService.createEmployee(employeeCreateDto);

        //compare expected vs actual result
        assertEquals(expectedEmployeeReturnDto, resultedEmployeeReturnDto);
    }

    @Test
    void updateEmployee() {

        EmployeeUpdateDto employeeUpdateDto = new EmployeeUpdateDto();
        employeeUpdateDto.setId(1L);
        employeeUpdateDto.setAddress("Str. Inteligenta");
        employeeUpdateDto.setName("Geanina Morosanu");
        employeeUpdateDto.setCity("Bucuresti");
        employeeUpdateDto.setPhoneNumber("0744555222");
        employeeUpdateDto.setEndDate(LocalDate.of(2028,11,11));

        EmployeeReturnDto expectedEmployeeReturnDto = new EmployeeReturnDto();
        expectedEmployeeReturnDto.setId(1L);
        expectedEmployeeReturnDto.setAddress("Str. Inteligenta");
        expectedEmployeeReturnDto.setName("Geanina Morosanu");
        expectedEmployeeReturnDto.setCity("Bucuresti");
        expectedEmployeeReturnDto.setPhoneNumber("0744555222");
        expectedEmployeeReturnDto.setEndDate(LocalDate.of(2028,11,11));

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setCity("Bucuresti");
        employee.setAddress("Str. Inteligenta");
        employee.setName("Geanina Morosanu");
        employee.setPhoneNumber("0744555222");
        employee.setEndDate(LocalDate.of(2028,11,11));

        Mockito.when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
        Mockito.when(employeeRepository.save(any())).thenReturn(employee);

        EmployeeReturnDto resultedEmployeeReturnDto = employeeService.updateEmployee(employeeUpdateDto);
        assertEquals(expectedEmployeeReturnDto, resultedEmployeeReturnDto);

    }

    @Test
    void getSuperiorIdByJobTitle() {

        Long expectedSuperiorIdByJobTitle = 1L;
        String jobTitle = "Sales Officer";

        Department department = new Department();
        department.setDepartmentName("SALES");
        department.setManagerId(1L);

        Mockito.when(departmentRepository.findByDepartmentName("SALES")).thenReturn(department);

        Long resultedSuperiorIdByJobTitle = employeeService.getSuperiorIdByJobTitle(jobTitle);
        assertEquals(expectedSuperiorIdByJobTitle, resultedSuperiorIdByJobTitle);
    }

    @Test
    void deleteEmployee() {

        Long employeeId = 1L;
        String expectedMessage = "This employee has been deleted!";

        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setSuperiorId(1L);
        employee.setLeaveRequests(new ArrayList<>());

        Manager manager = new Manager();
        manager.setEmployees(new ArrayList<>());
        manager.setLeaveRequestsToManage(new ArrayList<>());
        manager.setId(1L);

        User user = new User();
        user.setId(2L);
        employee.setUser(user);

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        Mockito.when(managerRepository.findById(any())).thenReturn(Optional.of(manager));
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(user));

        String resultedMessage = employeeService.deleteEmployee(employeeId);
        assertEquals(expectedMessage, resultedMessage);
    }


    @Test
    void getEmployeeRemainingDays() {

        String username = "maria_u@yahoo.com";
        Integer expectedEmployeeRemainingDays = 21;

        User user = new User();

        Employee employee = new Employee();
        employee.setAnnualLeaveDays(21);

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        Mockito.when(employeeRepository.findByUser(user)).thenReturn(Optional.of(employee));

        Integer resultedEmployeeRemainingDays = employeeService.getEmployeeRemainingDays(username);

        assertEquals(expectedEmployeeRemainingDays, resultedEmployeeRemainingDays);
    }
}