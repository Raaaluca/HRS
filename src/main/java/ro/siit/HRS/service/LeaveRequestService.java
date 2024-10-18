package ro.siit.HRS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.siit.HRS.dto.LeaveRequestCreateDto;
import ro.siit.HRS.dto.LeaveRequestReturnDto;
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

        return leaveRequestRepository.findById(id).orElseThrow();
    }

    public LeaveRequestReturnDto mapLeaveRequestReturnDto(LeaveRequest leaveRequest) {

        LeaveRequestReturnDto leaveRequestReturnDto = new LeaveRequestReturnDto();
        leaveRequestReturnDto.setNumberOfDaysForLeaveRequest(leaveRequest.getNumberOfDays());
        leaveRequestReturnDto.setTypeOfLeaveRequest(leaveRequest.getType());
        leaveRequestReturnDto.setEmployeeId(leaveRequest.getEmployeeId());
        leaveRequestReturnDto.setManagerId(leaveRequest.getManagerId());

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


        //    @OneToMany
        //    private List<LeaveRequest> leaveRequests;

        Employee employee = employeeRepository.findById(leaveRequestCreateDto.getEmployeeId()).orElseThrow();
        employee.getLeaveRequests().add(leaveRequest);
        employeeRepository.save(employee);

        Manager manager = managerRepository.findById(employee.getSuperiorId()).orElseThrow();
        manager.getLeaveRequestsToManage().add(leaveRequest);
        managerRepository.save(manager);

        return mapLeaveRequestReturnDto(leaveRequest);
    }
}
