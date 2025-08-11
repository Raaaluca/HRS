package ro.siit.HRS.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.siit.HRS.dto.create.LeaveRequestCreateDto;
import ro.siit.HRS.dto.rturn.LeaveRequestReturnDto;
import ro.siit.HRS.exceptions.EmployeeNotFoundException;
import ro.siit.HRS.exceptions.LeaveRequestNotFoundException;
import ro.siit.HRS.exceptions.ManagerNotFoundException;
import ro.siit.HRS.model.Employee;
import ro.siit.HRS.model.LeaveRequest;
import ro.siit.HRS.model.Manager;
import ro.siit.HRS.model.User;
import ro.siit.HRS.repository.EmployeeRepository;
import ro.siit.HRS.repository.LeaveRequestRepository;
import ro.siit.HRS.repository.ManagerRepository;
import ro.siit.HRS.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final ManagerRepository managerRepository;
    private final UserRepository userRepository;

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

    public LeaveRequestReturnDto mapLeaveRequestReturnDto(LeaveRequest leaveRequest) {

        LeaveRequestReturnDto leaveRequestReturnDto = new LeaveRequestReturnDto();
        leaveRequestReturnDto.setId(leaveRequest.getId());
        leaveRequestReturnDto.setNumberOfDaysForLeaveRequest(leaveRequest.getNumberOfDays());
        leaveRequestReturnDto.setTypeOfLeaveRequest(leaveRequest.getType());
        if (leaveRequest.getEmployeeId() != null) {
            leaveRequestReturnDto.setEmployeeName(getEmployeeNameById(leaveRequest.getEmployeeId()));
            leaveRequestReturnDto.setJobTitle(getJobTitle(leaveRequest.getEmployeeId()));
            leaveRequestReturnDto.setAnnualLeaveDays(getAnnualLeaveDaysByEmployeeId(leaveRequest.getEmployeeId()));
            leaveRequestReturnDto.setSuperiorName(getSuperiorNameBySuperiorId(leaveRequest.getManagerId()));
        }
        if (leaveRequest.isApproved()) {
            leaveRequestReturnDto.setStatus("APPROVED");
        } else {
            leaveRequestReturnDto.setStatus("PENDING...");
        }

        return leaveRequestReturnDto;
    }
    public String getSuperiorNameBySuperiorId(Long superiorId){

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
            employee.getLeaveRequests().add(leaveRequest);
            employee.setAnnualLeaveDays(employee.getAnnualLeaveDays() - leaveRequestCreateDto.getNumberOfDaysForLeaveRequest());
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
        return mapLeaveRequestReturnDto(leaveRequest);
    }
}
