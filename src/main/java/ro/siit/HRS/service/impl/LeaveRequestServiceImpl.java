package ro.siit.HRS.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.siit.HRS.dto.create.LeaveRequestCreateDto;
import ro.siit.HRS.dto.response.LeaveRequestReturnDto;
import ro.siit.HRS.exceptions.EmployeeNotFoundException;
import ro.siit.HRS.exceptions.InsufficientLeaveDaysException;
import ro.siit.HRS.exceptions.ManagerNotFoundException;
import ro.siit.HRS.model.Employee;
import ro.siit.HRS.model.LeaveRequest;
import ro.siit.HRS.model.Manager;
import ro.siit.HRS.model.User;
import ro.siit.HRS.repository.EmployeeRepository;
import ro.siit.HRS.repository.LeaveRequestRepository;
import ro.siit.HRS.repository.ManagerRepository;
import ro.siit.HRS.repository.UserRepository;
import ro.siit.HRS.service.LeaveRequestService;
import ro.siit.HRS.util.MapperUtil;

@Service
@RequiredArgsConstructor
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final ManagerRepository managerRepository;
    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;

    public String getEmployeeNameById(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        return employee.getName();
    }

    public String getJobTitle(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        return employee.getJobTitle();
    }

    public Integer getAnnualLeaveDaysByEmployeeId(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        return employee.getAnnualLeaveDays();
    }

    public String getSuperiorNameBySuperiorId(Long superiorId) {

        Manager manager = managerRepository.findById(superiorId).orElseThrow();
        return manager.getName();
    }

    public void createLeaveRequestByUsername(LeaveRequestCreateDto leaveRequestCreateDto, String username) {

        User user = userRepository.findByUsername(username).orElseThrow();
        Manager manager = managerRepository.findByUser(user).orElseThrow();

        leaveRequestCreateDto.setManagerId(manager.getId());
        createLeaveRequest(leaveRequestCreateDto);
    }

    public void createEmployeeLeaveRequestByUsername(LeaveRequestCreateDto leaveRequestCreateDto, String username) {

        User user = userRepository.findByUsername(username).orElseThrow();
        Employee employee = employeeRepository.findByUser(user).orElseThrow();

        leaveRequestCreateDto.setEmployeeId(employee.getId());
        leaveRequestCreateDto.setManagerId(employee.getSuperiorId());
        createLeaveRequest(leaveRequestCreateDto);
    }

    public LeaveRequestReturnDto createLeaveRequest(LeaveRequestCreateDto leaveRequestCreateDto) {

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setType(leaveRequestCreateDto.getTypeOfLeaveRequest());
        leaveRequest.setNumberOfDays(leaveRequestCreateDto.getNumberOfDaysForLeaveRequest());
        leaveRequest.setManagerId(leaveRequestCreateDto.getManagerId());
        leaveRequest.setEmployeeId(leaveRequestCreateDto.getEmployeeId());
        leaveRequest.setApproved(false);
        leaveRequest = leaveRequestRepository.save(leaveRequest);

        if (leaveRequestCreateDto.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(leaveRequestCreateDto.getEmployeeId()).orElseThrow(()
                    -> new EmployeeNotFoundException("This employee id " + leaveRequestCreateDto.getEmployeeId() + "was not found!"));
            int remaining = employee.getAnnualLeaveDays();
            int requested = leaveRequestCreateDto.getNumberOfDaysForLeaveRequest();
            if (requested > remaining) {
                throw new InsufficientLeaveDaysException("Requested " + requested + " days, but only " + remaining + " remaining");
            }
            employee.getLeaveRequests().add(leaveRequest);
            employee.setAnnualLeaveDays(remaining - requested);
            employeeRepository.save(employee);

            Manager manager = managerRepository.findById(employee.getSuperiorId()).orElseThrow(()
                    -> new ManagerNotFoundException("This manager id " + employee.getSuperiorId() + "was not found!"));
            manager.getLeaveRequestsToManage().add(leaveRequest);
            managerRepository.save(manager);
        } else {
            Manager manager = managerRepository.findById(leaveRequestCreateDto.getManagerId()).orElseThrow(()
                    -> new ManagerNotFoundException("This manager id " + leaveRequestCreateDto.getManagerId() + "was not found!"));
            manager.getLeaveRequests().add(leaveRequest);
            manager.setAnnualLeaveDays(manager.getAnnualLeaveDays() - leaveRequestCreateDto.getNumberOfDaysForLeaveRequest());
            managerRepository.save(manager);
        }
        return mapperUtil.mapLeaveRequestReturnDto(leaveRequest);
    }
}
