package ro.siit.HRS.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.siit.HRS.dto.create.EmployeeCreateDto;
import ro.siit.HRS.dto.response.EmployeeReturnDto;
import ro.siit.HRS.dto.update.EmployeeUpdateDto;
import ro.siit.HRS.model.Department;
import ro.siit.HRS.model.Employee;
import ro.siit.HRS.model.Manager;
import ro.siit.HRS.model.User;
import ro.siit.HRS.repository.DepartmentRepository;
import ro.siit.HRS.repository.EmployeeRepository;
import ro.siit.HRS.repository.ManagerRepository;
import ro.siit.HRS.repository.UserRepository;
import ro.siit.HRS.service.impl.EmployeeServiceImpl;
import ro.siit.HRS.util.MapperUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    public static final int ANNUAL_LEAVE_DAYS = 21;
    public static final String SALES_OFFICER = "Sales Officer";
    public static final String DEPARTMENT_NAME_SALES = "SALES";
    public static final String USERNAME = "maria_u@yahoo.com";

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
    private EmployeeServiceImpl employeeService;
    @Mock
    private MapperUtil mapperUtil;

    private static final Long EMPLOYEE_ID = 1L;
    private static final Long MANAGER_ID  = 5L;
    private static final Long USER_ID     = 10L;

    @Test
    void createEmployee() {

        //initial data

        User user = new User();
        user.setId(USER_ID);

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
        employeeCreateDto.setSuperiorId(MANAGER_ID);

        //expected result after method call
        EmployeeReturnDto expectedEmployeeReturnDto = new EmployeeReturnDto();
        expectedEmployeeReturnDto.setId(EMPLOYEE_ID);
        expectedEmployeeReturnDto.setName("Daniel Roibu");
        expectedEmployeeReturnDto.setPhoneNumber("0733125775");
        expectedEmployeeReturnDto.setEmail("daniel_r@yahoo.com");
        expectedEmployeeReturnDto.setSuperiorId(MANAGER_ID);
        expectedEmployeeReturnDto.setAddress("Str. Infratirii");
        expectedEmployeeReturnDto.setCity("Timisoara");
        expectedEmployeeReturnDto.setGender("male");
        expectedEmployeeReturnDto.setStartDate(LocalDate.of(2023,11,11));
        expectedEmployeeReturnDto.setEndDate(LocalDate.of(2026,9,4));
        expectedEmployeeReturnDto.setJobTitle("Payroll Admin");
        expectedEmployeeReturnDto.setAnnualLeaveDays(ANNUAL_LEAVE_DAYS);

        Employee employee = new Employee();
        employee.setId(EMPLOYEE_ID);
        employee.setSuperiorId(MANAGER_ID);
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
        employee.setUser(user);
        employee.setAnnualLeaveDays(ANNUAL_LEAVE_DAYS);

        Manager manager = new Manager();
        manager.setId(MANAGER_ID);
        manager.setEmployees(new ArrayList<>());
        manager.getEmployees().add(employee);

        when(employeeRepository.save(any())).thenReturn(employee);
        when(managerRepository.findById((any()))).thenReturn(Optional.of(manager));
        when(mapperUtil.mapEmployeeEntity(employeeCreateDto)).thenReturn(employee);
        when(mapperUtil.mapEmployee(employee)).thenReturn(expectedEmployeeReturnDto);

        //actual result after method call
        EmployeeReturnDto resultedEmployeeReturnDto = employeeService.createEmployee(employeeCreateDto);

        //compare expected vs actual result
        assertEquals(expectedEmployeeReturnDto, resultedEmployeeReturnDto);
    }

    @Test
    void updateEmployee() {

        EmployeeUpdateDto employeeUpdateDto = new EmployeeUpdateDto();
        employeeUpdateDto.setId(EMPLOYEE_ID);
        employeeUpdateDto.setAddress("Str. Inteligenta");
        employeeUpdateDto.setName("Geanina Morosanu");
        employeeUpdateDto.setCity("Cluj-Napoca");
        employeeUpdateDto.setPhoneNumber("0744555222");
        employeeUpdateDto.setEndDate(LocalDate.of(2028,11,11));

        EmployeeReturnDto expectedEmployeeReturnDto = new EmployeeReturnDto();
        expectedEmployeeReturnDto.setId(EMPLOYEE_ID);
        expectedEmployeeReturnDto.setAddress("Str. Inteligenta");
        expectedEmployeeReturnDto.setName("Geanina Morosanu");
        expectedEmployeeReturnDto.setCity("Cluj-Napoca");
        expectedEmployeeReturnDto.setPhoneNumber("0744555222");
        expectedEmployeeReturnDto.setEndDate(LocalDate.of(2028,11,11));

        Employee employee = new Employee();
        employee.setId(EMPLOYEE_ID);
        employee.setSuperiorId(MANAGER_ID);
        employee.setCity("Cluj-Napoca");
        employee.setAddress("Str. Inteligenta");
        employee.setName("Geanina Morosanu");
        employee.setPhoneNumber("0744555222");
        employee.setEndDate(LocalDate.of(2028,11,11));

        when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any())).thenReturn(employee);
        when(mapperUtil.mapEmployee(employee)).thenReturn(expectedEmployeeReturnDto);

        EmployeeReturnDto resultedEmployeeReturnDto = employeeService.updateEmployee(employeeUpdateDto);
        assertEquals(expectedEmployeeReturnDto, resultedEmployeeReturnDto);

    }

    @Test
    void getSuperiorIdByJobTitle() {

        Department department = new Department();
        department.setDepartmentName(DEPARTMENT_NAME_SALES);
        department.setManagerId(MANAGER_ID);

        when(departmentRepository.findByDepartmentName(DEPARTMENT_NAME_SALES)).thenReturn(department);

        Long resultedSuperiorIdByJobTitle = employeeService.getSuperiorIdByJobTitle(SALES_OFFICER);
        assertEquals(MANAGER_ID, resultedSuperiorIdByJobTitle);
    }

    @Test
    void deleteEmployee() {

        User user = new User();
        user.setId(USER_ID);

        Employee employee = new Employee();
        employee.setId(EMPLOYEE_ID);
        employee.setSuperiorId(MANAGER_ID);
        employee.setLeaveRequests(new ArrayList<>());
        employee.setUser(user);

        Manager manager = new Manager();
        manager.setId(MANAGER_ID);
        manager.setEmployees(new ArrayList<>());
        manager.getEmployees().add(employee);
        manager.setLeaveRequestsToManage(new ArrayList<>());


        when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.of(employee));
        when(managerRepository.findById(MANAGER_ID)).thenReturn(Optional.of(manager));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        employeeService.deleteEmployee(EMPLOYEE_ID);
        assertTrue(manager.getEmployees().isEmpty());
    }


    @Test
    void getEmployeeRemainingDays() {

        User user = new User();
        Employee employee = new Employee();
        employee.setAnnualLeaveDays(ANNUAL_LEAVE_DAYS);

        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));
        when(employeeRepository.findByUser(user)).thenReturn(Optional.of(employee));

        Integer resultedEmployeeRemainingDays = employeeService.getEmployeeRemainingDays(USERNAME);

        assertEquals(ANNUAL_LEAVE_DAYS, resultedEmployeeRemainingDays);
    }
}