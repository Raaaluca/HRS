package ro.siit.HRS.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ro.siit.HRS.dto.rturn.DepartmentReturnDto;
import ro.siit.HRS.dto.rturn.EmployeeReturnDto;
import ro.siit.HRS.dto.rturn.LeaveRequestReturnDto;
import ro.siit.HRS.dto.rturn.ManagerReturnDto;
import ro.siit.HRS.dto.update.ManagerUpdateDto;
import ro.siit.HRS.model.Department;
import ro.siit.HRS.model.Employee;
import ro.siit.HRS.model.LeaveRequest;
import ro.siit.HRS.model.Manager;
import ro.siit.HRS.service.impl.LeaveRequestServiceImpl;

@Component
public class MapperUtil {

    @Autowired
    @Lazy
    private LeaveRequestServiceImpl leaveRequestService;

    public EmployeeReturnDto mapEmployee(Employee employee) {

        EmployeeReturnDto employeeReturnDto = new EmployeeReturnDto();
        employeeReturnDto.setId(employee.getId());
        employeeReturnDto.setGender(employee.getGender());
        employeeReturnDto.setName(employee.getName());
        employeeReturnDto.setPhoneNumber(employee.getPhoneNumber());
        employeeReturnDto.setAddress(employee.getAddress());
        employeeReturnDto.setCity(employee.getCity());
        employeeReturnDto.setEmail(employee.getEmail());
        employeeReturnDto.setSuperiorId(employee.getSuperiorId());
        employeeReturnDto.setSuperiorName(leaveRequestService.getSuperiorNameBySuperiorId(employee.getSuperiorId()));
        employeeReturnDto.setStartDate(employee.getStartDate());
        employeeReturnDto.setEndDate(employee.getEndDate());
        employeeReturnDto.setJobTitle(employee.getJobTitle());
        employeeReturnDto.setPhoneNumber(employee.getPhoneNumber());
        employeeReturnDto.setAnnualLeaveDays(employee.getAnnualLeaveDays());

        return employeeReturnDto;
    }

    public EmployeeReturnDto mapManagerToEmployeeReturnDto(Manager manager) {

        EmployeeReturnDto employeeReturnDto = new EmployeeReturnDto();
        employeeReturnDto.setId(manager.getId());
        employeeReturnDto.setSuperiorName("-");
        employeeReturnDto.setGender(manager.getGender());
        employeeReturnDto.setName(manager.getName());
        employeeReturnDto.setPhoneNumber(manager.getPhoneNumber());
        employeeReturnDto.setAddress(manager.getAddress());
        employeeReturnDto.setCity(manager.getCity());
        employeeReturnDto.setEmail(manager.getEmail());
        employeeReturnDto.setStartDate(manager.getStartDate());
        employeeReturnDto.setEndDate(manager.getEndDate());
        employeeReturnDto.setJobTitle(manager.getJobTitle());
        employeeReturnDto.setPhoneNumber(manager.getPhoneNumber());
        employeeReturnDto.setAnnualLeaveDays(manager.getAnnualLeaveDays());

        return employeeReturnDto;
    }

    public DepartmentReturnDto mapDepartment(Department department) {

        DepartmentReturnDto departmentReturnDto = new DepartmentReturnDto();
        departmentReturnDto.setDepartmentName(department.getDepartmentName());
        departmentReturnDto.setManagerId(department.getManagerId());

        return departmentReturnDto;
    }

    public LeaveRequestReturnDto mapLeaveRequestReturnDto(LeaveRequest leaveRequest) {

        LeaveRequestReturnDto leaveRequestReturnDto = new LeaveRequestReturnDto();
        leaveRequestReturnDto.setId(leaveRequest.getId());
        leaveRequestReturnDto.setNumberOfDaysForLeaveRequest(leaveRequest.getNumberOfDays());
        leaveRequestReturnDto.setTypeOfLeaveRequest(leaveRequest.getType());
        if (leaveRequest.getEmployeeId() != null) {
            leaveRequestReturnDto.setEmployeeName(leaveRequestService.getEmployeeNameById(leaveRequest.getEmployeeId()));
            leaveRequestReturnDto.setJobTitle(leaveRequestService.getJobTitle(leaveRequest.getEmployeeId()));
            leaveRequestReturnDto.setAnnualLeaveDays(leaveRequestService.getAnnualLeaveDaysByEmployeeId(leaveRequest.getEmployeeId()));
            leaveRequestReturnDto.setSuperiorName(leaveRequestService.getSuperiorNameBySuperiorId(leaveRequest.getManagerId()));
        }
        if (leaveRequest.isApproved()) {
            leaveRequestReturnDto.setStatus("APPROVED");
        } else {
            leaveRequestReturnDto.setStatus("PENDING...");
        }

        return leaveRequestReturnDto;
    }

    public ManagerReturnDto mapManager(Manager manager) {

        ManagerReturnDto managerReturnDto = new ManagerReturnDto();
        managerReturnDto.setId(manager.getId());
        managerReturnDto.setAddress(manager.getAddress());
        managerReturnDto.setCity(manager.getCity());
        managerReturnDto.setEmail(manager.getEmail());
        managerReturnDto.setName(manager.getName());
        managerReturnDto.setGender(manager.getGender());
        managerReturnDto.setNationalId(manager.getNationalId());
        managerReturnDto.setPhoneNumber(manager.getPhoneNumber());
        managerReturnDto.setStartDate(manager.getStartDate());
        managerReturnDto.setEndDate(manager.getEndDate());
        managerReturnDto.setAnnualLeaveDays(manager.getAnnualLeaveDays());

        return managerReturnDto;
    }

    public ManagerUpdateDto mapManagerUpdate(Manager manager) {

        ManagerUpdateDto managerUpdateDto = new ManagerUpdateDto();
        managerUpdateDto.setId(manager.getId());
        managerUpdateDto.setAddress(manager.getAddress());
        managerUpdateDto.setCity(manager.getCity());
        managerUpdateDto.setEmail(manager.getEmail());
        managerUpdateDto.setName(manager.getName());
        managerUpdateDto.setPhoneNumber(manager.getPhoneNumber());
        managerUpdateDto.setEndDate(manager.getEndDate());
        managerUpdateDto.setAnnualLeaveDays(manager.getAnnualLeaveDays());
        managerUpdateDto.setJobTitle(manager.getJobTitle());

        return managerUpdateDto;
    }
}
