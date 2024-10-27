package ro.siit.HRS.service;

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
import ro.siit.HRS.repository.EmployeeRepository;
import ro.siit.HRS.repository.LeaveRequestRepository;
import ro.siit.HRS.repository.ManagerRepository;

@Service
public class LeaveRequestService {
    @Autowired
    private LeaveRequestRepository leaveRequestRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ManagerRepository managerRepository;

    public LeaveRequest findById(Long id) {

        return leaveRequestRepository.findById(id)
                .orElseThrow(() -> new LeaveRequestNotFoundException("This Leave Request id " + id + "was not found!"));
    }

    public String getEmployeeNameById(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        return employee.getName();
    }

    public LeaveRequestReturnDto mapLeaveRequestReturnDto(LeaveRequest leaveRequest) {

        LeaveRequestReturnDto leaveRequestReturnDto = new LeaveRequestReturnDto();
        leaveRequestReturnDto.setId(leaveRequest.getId());
        leaveRequestReturnDto.setNumberOfDaysForLeaveRequest(leaveRequest.getNumberOfDays());
        leaveRequestReturnDto.setTypeOfLeaveRequest(leaveRequest.getType());
        leaveRequestReturnDto.setEmployeeName(getEmployeeNameById(leaveRequest.getEmployeeId()));
        if (leaveRequest.isApproved()) {
            leaveRequestReturnDto.setStatus("APPROVED");
        } else {
            leaveRequestReturnDto.setStatus("PENDING...");
        }

        return leaveRequestReturnDto;
    }

    public LeaveRequestReturnDto createLeaveRequest(LeaveRequestCreateDto leaveRequestCreateDto) {

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setType(leaveRequestCreateDto.getTypeOfLeaveRequest());
        leaveRequest.setNumberOfDays(leaveRequestCreateDto.getNumberOfDaysForLeaveRequest());
        leaveRequest.setManagerId(leaveRequestCreateDto.getManagerId());
        leaveRequest.setEmployeeId(leaveRequestCreateDto.getEmployeeId());
        leaveRequest.setApproved(false);
        leaveRequest = leaveRequestRepository.save(leaveRequest);

        Employee employee = employeeRepository.findById(leaveRequestCreateDto.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException("This employee id " + leaveRequestCreateDto.getEmployeeId() + "was not found!"));
        employee.getLeaveRequests().add(leaveRequest);
        employeeRepository.save(employee);

        Manager manager = managerRepository.findById(employee.getSuperiorId())
                .orElseThrow(() -> new ManagerNotFoundException("This manager id " + employee.getSuperiorId() + "was not found!"));
        manager.getLeaveRequestsToManage().add(leaveRequest);
        managerRepository.save(manager);

        return mapLeaveRequestReturnDto(leaveRequest);
    }

}
