package ro.siit.HRS.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ro.siit.HRS.dto.create.LeaveRequestCreateDto;
import ro.siit.HRS.dto.rturn.LeaveRequestReturnDto;
import ro.siit.HRS.model.Employee;
import ro.siit.HRS.model.LeaveRequest;
import ro.siit.HRS.model.Manager;
import ro.siit.HRS.repository.EmployeeRepository;
import ro.siit.HRS.repository.LeaveRequestRepository;
import ro.siit.HRS.repository.ManagerRepository;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class LeaveRequestServiceTest {

    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private LeaveRequestRepository leaveRequestRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private LeaveRequestService leaveRequestService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getEmployeeNameById() {

        String expectedEmployeeNameById = "Florenta Gutan";
        Long EmployeeNameByIdParameter = 1L;

        Employee employee = new Employee();
        employee.setId(EmployeeNameByIdParameter);
        employee.setName("Florenta Gutan");

        Mockito.when(employeeRepository.findById(EmployeeNameByIdParameter)).thenReturn(Optional.of(employee));

        String resultedEmployeeNameById = leaveRequestService.getEmployeeNameById(EmployeeNameByIdParameter);
        assertEquals(expectedEmployeeNameById, resultedEmployeeNameById);
    }

    @Test
    void getJobTitle() {

        String expectedJobTitle = "HR Admin";
        Long employeeIdParameter = 1L;

        Employee employee = new Employee();
        employee.setId(employeeIdParameter);
        employee.setJobTitle("HR Admin");

        Mockito.when(employeeRepository.findById(employeeIdParameter)).thenReturn(Optional.of(employee));

        String resultedJobTitle = leaveRequestService.getJobTitle(employeeIdParameter);
        assertEquals(expectedJobTitle, resultedJobTitle);
    }

    public Integer getAnnualLeaveDaysByEmployeeId(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        return employee.getAnnualLeaveDays();
    }

    @Test
    void getAnnualLeaveDaysByEmployeeId() {

        Integer expectedAnnualLeaveDaysByEmployeeId = 21;
        Long employeeIdParameter = 1L;

        Employee employee = new Employee();
        employee.setId(employeeIdParameter);
        employee.setAnnualLeaveDays(21);

        Mockito.when(employeeRepository.findById(employeeIdParameter)).thenReturn(Optional.of(employee));

        Integer resultedAnnualLeaveDaysByEmployeeId = leaveRequestService.getAnnualLeaveDaysByEmployeeId
                (employeeIdParameter);
        assertEquals(expectedAnnualLeaveDaysByEmployeeId, resultedAnnualLeaveDaysByEmployeeId);
    }

    @Test
    void getSuperiorNameBySuperiorId() {

        String expectedSuperiorNameBySuperiorId = "Damian Covaci";
        Long superiorIdParameter = 1L;

        Manager manager = new Manager();
        manager.setId(superiorIdParameter);
        manager.setName("Damian Covaci");

        Mockito.when(managerRepository.findById(superiorIdParameter)).thenReturn(Optional.of(manager));

        String resultedSuperiorNameBySuperiorId = leaveRequestService.getSuperiorNameBySuperiorId(superiorIdParameter);
        assertEquals(expectedSuperiorNameBySuperiorId, resultedSuperiorNameBySuperiorId);
    }

    @Test
    void createLeaveRequest() {

        LeaveRequestCreateDto leaveRequestCreateDto = new LeaveRequestCreateDto();
        leaveRequestCreateDto.setEmployeeId(1L);
        leaveRequestCreateDto.setManagerId(1L);
        leaveRequestCreateDto.setTypeOfLeaveRequest("Holiday");
        leaveRequestCreateDto.setNumberOfDaysForLeaveRequest(12);

        LeaveRequestReturnDto expectedLeaveRequestReturnDto = new LeaveRequestReturnDto();
        expectedLeaveRequestReturnDto.setAnnualLeaveDays(9);
        expectedLeaveRequestReturnDto.setTypeOfLeaveRequest("Holiday");
        expectedLeaveRequestReturnDto.setId(1L);
        expectedLeaveRequestReturnDto.setNumberOfDaysForLeaveRequest(12);
        expectedLeaveRequestReturnDto.setJobTitle("HR Admin");
        expectedLeaveRequestReturnDto.setEmployeeName("Daiana Popescu");
        expectedLeaveRequestReturnDto.setSuperiorName("Ion Ion");
        expectedLeaveRequestReturnDto.setStatus("PENDING...");

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setId(1L);
        leaveRequest.setApproved(false);
        leaveRequest.setType("Holiday");
        leaveRequest.setNumberOfDays(12);
        leaveRequest.setEmployeeId(1L);
        leaveRequest.setManagerId(1L);

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Daiana Popescu");
        employee.setSuperiorId(1L);
        employee.setJobTitle("HR Admin");
        employee.setLeaveRequests(new ArrayList<>());
        employee.setAnnualLeaveDays(21);

        Manager manager = new Manager();
        manager.setId(1L);
        manager.setName("Ion Ion");
        manager.setLeaveRequestsToManage(new ArrayList<>());

        Mockito.when(leaveRequestRepository.save(any())).thenReturn(leaveRequest);
        Mockito.when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
        Mockito.when(managerRepository.findById(any())).thenReturn(Optional.of(manager));

        LeaveRequestReturnDto resultedLeaveRequestDto = leaveRequestService.createLeaveRequest(leaveRequestCreateDto);

        assertEquals(expectedLeaveRequestReturnDto, resultedLeaveRequestDto);
    }
}