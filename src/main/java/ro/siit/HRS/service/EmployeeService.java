package ro.siit.HRS.service;

import ro.siit.HRS.dto.create.EmployeeCreateDto;
import ro.siit.HRS.dto.response.EmployeeReturnDto;
import ro.siit.HRS.dto.response.LeaveRequestReturnDto;
import ro.siit.HRS.dto.update.EmployeeUpdateDto;

import java.util.List;

public interface EmployeeService {

    EmployeeReturnDto findById(Long id);
    EmployeeReturnDto createEmployee(EmployeeCreateDto employeeCreateDto);
    void deleteEmployee(Long employeeId);
    EmployeeReturnDto updateEmployee(EmployeeUpdateDto employeeUpdateDto);
    String getAuthenticationDetails(String username);
    EmployeeReturnDto getUpdatePersonalDetails(String username);
    void updateEmployeeDto(EmployeeUpdateDto employeeUpdateDto);
    List<LeaveRequestReturnDto> myLeaveRequests(String username);
    Integer getEmployeeRemainingDays(String username);
    Long getSuperiorIdByJobTitle(String jobTitle);

}
