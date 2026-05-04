package ro.siit.HRS.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.siit.HRS.dto.create.ManagerCreateDto;
import ro.siit.HRS.dto.response.LeaveRequestReturnDto;
import ro.siit.HRS.dto.response.ManagerReturnDto;
import ro.siit.HRS.dto.update.EmployeeUpdateDto;
import ro.siit.HRS.dto.update.ManagerUpdateDto;
import ro.siit.HRS.exceptions.ManagerNotFoundException;
import ro.siit.HRS.model.*;
import ro.siit.HRS.repository.*;
import ro.siit.HRS.service.ManagerService;
import ro.siit.HRS.util.MapperUtil;
import ro.siit.HRS.util.Role;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final PasswordEncoder passwordEncoder;
    private final LeaveRequestRepository leaveRequestRepository;
    private final UserRepository userRepository;
    private final ManagerRepository managerRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final MapperUtil mapperUtil;

    public ManagerReturnDto findById(Long id) {

        return mapperUtil.mapManager(managerRepository.findById(id)
                .orElseThrow(() -> new ManagerNotFoundException(
                        "This manager id " + id + " does not exist!")));
    }

    @Transactional
    public ManagerReturnDto createManager(ManagerCreateDto managerCreateDto) {

        Manager manager = new Manager();

        User user = new User();
        user.setRole(Role.MANAGER.name());
        user.setUsername(managerCreateDto.getEmail());
        user.setPassword(passwordEncoder.encode(managerCreateDto.getNationalId()));
        user = userRepository.save(user);

        manager = mapperUtil.mapManagerEntity(managerCreateDto);
        manager.setUser(user);
        manager = managerRepository.save(manager);

        return mapperUtil.mapManager(manager);
    }

    public void updateManagerFromEmployeeDto(EmployeeUpdateDto employeeUpdateDto) {

        Manager manager = managerRepository.findById(employeeUpdateDto.getId()).orElseThrow(()
                -> new ManagerNotFoundException("This manager id " + employeeUpdateDto.getId() + " can not be found!!"));
        applyManagerUpdates(manager, employeeUpdateDto.getAddress(), employeeUpdateDto.getName(), employeeUpdateDto.getCity(), employeeUpdateDto.getEmail(), employeeUpdateDto.getEndDate(), employeeUpdateDto.getPhoneNumber());

        managerRepository.save(manager);
    }

    private void applyManagerUpdates(Manager manager, String address, String name, String city, String email, LocalDate endDate, String phoneNumber) {
        if (address != null) {
            manager.setAddress(address);
        }
        if (name != null) {
            manager.setName(name);
        }
        if (city != null) {
            manager.setCity(city);
        }
        if (email != null) {
            manager.setEmail(email);
            manager.getUser().setUsername(manager.getEmail());
        }
        if (endDate != null) {
            manager.setEndDate(endDate);
        }
        if (phoneNumber != null) {
            manager.setPhoneNumber(phoneNumber);
        }
    }

    public void updateManagerDto(ManagerUpdateDto managerUpdateDto) {

        Manager manager = managerRepository.findById(managerUpdateDto.getId()).orElseThrow(()
                -> new ManagerNotFoundException("This manager id " + managerUpdateDto.getId() + " can not be found!!"));
        applyManagerUpdates(manager, managerUpdateDto.getAddress(), managerUpdateDto.getName(), managerUpdateDto.getCity(), managerUpdateDto.getEmail(), managerUpdateDto.getEndDate(), managerUpdateDto.getPhoneNumber());

        managerRepository.save(manager);
    }

    public ManagerReturnDto updateManager(ManagerUpdateDto managerUpdateDto) {

        Manager manager = managerRepository.findById(managerUpdateDto.getId()).orElseThrow(()
                -> new ManagerNotFoundException("This manager id " + managerUpdateDto.getId() + " can not be found!!"));
        applyManagerUpdates(manager, managerUpdateDto.getAddress(), managerUpdateDto.getName(), managerUpdateDto.getCity(), managerUpdateDto.getEmail(), managerUpdateDto.getEndDate(), managerUpdateDto.getPhoneNumber());

        manager = managerRepository.save(manager);

        return mapperUtil.mapManager(manager);
    }

    public ManagerReturnDto assignEmployeeToManager(Long employeeId, Long managerId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        Manager manager = managerRepository.findById(managerId).orElseThrow();
        manager.getEmployees().add(employee);
        manager = managerRepository.save(manager);
        return mapperUtil.mapManager(manager);
    }

    @Transactional
    public String deleteManager(Long managerId) {

        Manager manager = managerRepository.findById(managerId).orElseThrow(()
                -> new ManagerNotFoundException("This manager id " + managerId + " was not found!"));

        managerRepository.deleteById(managerId);
        userRepository.deleteById(manager.getUser().getId());
        return "This manager has been deleted!";
    }

    public List<LeaveRequestReturnDto> getManagerPendingLeaveRequests(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();
        Manager manager = managerRepository.findByUser(user).orElseThrow();

        return manager
                .getLeaveRequestsToManage()
                .stream()
                .map(mapperUtil::mapLeaveRequestReturnDto)
                .toList();
    }

    public List<LeaveRequestReturnDto> myLeaveRequests(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();
        Manager manager = managerRepository.findByUser(user).orElseThrow();

        return manager
                .getLeaveRequests()
                .stream()
                .map(mapperUtil::mapLeaveRequestReturnDto)
                .toList();
    }

    public String getAuthenticationDetails(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();
        Manager manager = managerRepository.findByUser(user).orElseThrow();
        Department department = departmentRepository.findByManagerId(manager.getId()).orElseThrow();

        return manager.getName() + ", " + department.getDepartmentName() + " Manager";
    }

    public void approveLeaveRequest(Long leaveRequestId) {

        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId).orElseThrow();
        leaveRequest.setApproved(true);
        leaveRequestRepository.save(leaveRequest);
    }

    public ManagerUpdateDto getUpdatePersonalDetails(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();
        Manager manager = managerRepository.findByUser(user).orElseThrow();

        return mapperUtil.mapManagerUpdate(manager);
    }

    public Integer getManagerRemainingDays(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();
        Manager manager = managerRepository.findByUser(user).orElseThrow();

        return manager.getAnnualLeaveDays();
    }
    
}
