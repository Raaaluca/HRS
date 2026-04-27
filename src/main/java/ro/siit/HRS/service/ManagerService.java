package ro.siit.HRS.service;

import ro.siit.HRS.dto.create.ManagerCreateDto;
import ro.siit.HRS.dto.response.LeaveRequestReturnDto;
import ro.siit.HRS.dto.response.ManagerReturnDto;
import ro.siit.HRS.dto.update.EmployeeUpdateDto;
import ro.siit.HRS.dto.update.ManagerUpdateDto;

import java.util.List;

public interface ManagerService {

    ManagerReturnDto findById(Long id);
    ManagerReturnDto createManager(ManagerCreateDto managerCreateDto);
    ManagerReturnDto assignEmployeeToManager(Long employeeId, Long managerId);
    String deleteManager(Long managerId);
    ManagerReturnDto updateManager(ManagerUpdateDto managerUpdateDto);
    ManagerUpdateDto getUpdatePersonalDetails(String username);
    void updateManagerFromEmployeeDto(EmployeeUpdateDto employeeUpdateDto);
    String getAuthenticationDetails(String username);
    void updateManagerDto(ManagerUpdateDto managerUpdateDto);
    List<LeaveRequestReturnDto> myLeaveRequests(String username);
    Integer getManagerRemainingDays(String username);
    List<LeaveRequestReturnDto> getManagerPendingLeaveRequests(String username);
    void approveLeaveRequest(Long leaveRequestId);
}
