package ro.siit.HRS.service;

import ro.siit.HRS.dto.create.LeaveRequestCreateDto;
import ro.siit.HRS.dto.rturn.LeaveRequestReturnDto;

public interface LeaveRequestService {

    LeaveRequestReturnDto createLeaveRequest(LeaveRequestCreateDto leaveRequestCreateDto);
    void createEmployeeLeaveRequestByUsername(LeaveRequestCreateDto leaveRequestCreateDto, String username);
    void createLeaveRequestByUsername(LeaveRequestCreateDto leaveRequestCreateDto, String username);
    String getSuperiorNameBySuperiorId(Long superiorId);
    String getEmployeeNameById(Long employeeId);
    String getJobTitle(Long employeeId);
    Integer getAnnualLeaveDaysByEmployeeId(Long employeeId);
}
