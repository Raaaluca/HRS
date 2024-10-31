package ro.siit.HRS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.siit.HRS.dto.create.ManagerCreateDto;
import ro.siit.HRS.dto.rturn.LeaveRequestReturnDto;
import ro.siit.HRS.dto.rturn.ManagerReturnDto;
import ro.siit.HRS.dto.update.EmployeeUpdateDto;
import ro.siit.HRS.dto.update.ManagerUpdateDto;
import ro.siit.HRS.exceptions.ManagerNotFoundException;
import ro.siit.HRS.model.*;
import ro.siit.HRS.repository.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LeaveRequestService leaveRequestService;
    @Autowired
    private LeaveRequestRepository leaveRequestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

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

    public ManagerReturnDto findById(Long id) {

        return mapManager(managerRepository.findById(id)
                .orElseThrow(() -> new ManagerNotFoundException(
                        "This manager id " + id + "does not exist!")));
    }

    public ManagerReturnDto createManager(ManagerCreateDto managerCreateDto) {

        Manager manager = new Manager();

        User user = new User();
        user.setRole("MANAGER");
        user.setUsername(managerCreateDto.getEmail());
        user.setPassword(passwordEncoder.encode(managerCreateDto.getNationalId()));
        user = userRepository.save(user);

        manager.setUser(user);
        manager.setAddress(managerCreateDto.getAddress());
        manager.setEmail(managerCreateDto.getEmail());
        manager.setCity(managerCreateDto.getCity());
        manager.setGender(managerCreateDto.getGender());
        manager.setName(managerCreateDto.getName());
        manager.setStartDate(managerCreateDto.getStartDate());
        manager.setEndDate(managerCreateDto.getEndDate());
        manager.setNationalId(managerCreateDto.getNationalId());
        manager.setPhoneNumber(managerCreateDto.getPhoneNumber());
        manager = managerRepository.save(manager);
        return mapManager(manager);
    }

    public void updateManagerFromEmployeeDto(EmployeeUpdateDto employeeUpdateDto) {

            Manager manager = managerRepository.findById(employeeUpdateDto.getId())
                    .orElseThrow(() -> new ManagerNotFoundException(
                            "This manager id " + employeeUpdateDto.getId() + "can not be found!!"));
            if (employeeUpdateDto.getAddress() != null) {
                manager.setAddress(employeeUpdateDto.getAddress());
            }
            if (employeeUpdateDto.getName() != null) {
                manager.setName(employeeUpdateDto.getName());
            }
            if (employeeUpdateDto.getCity() != null) {
                manager.setCity(employeeUpdateDto.getCity());
            }
            if (employeeUpdateDto.getEmail() != null) {
                manager.setEmail(employeeUpdateDto.getEmail());
                manager.getUser().setUsername(manager.getEmail());
            }
            if (employeeUpdateDto.getEndDate() != null) {
                manager.setEndDate(employeeUpdateDto.getEndDate());
            }
            if (employeeUpdateDto.getPhoneNumber() != null) {
                manager.setPhoneNumber(employeeUpdateDto.getPhoneNumber());
            }
            managerRepository.save(manager);
    }

    public void updateManagerDto(ManagerUpdateDto managerUpdateDto) {

        Manager manager = managerRepository.findById(managerUpdateDto.getId())
                .orElseThrow(() -> new ManagerNotFoundException(
                        "This manager id " + managerUpdateDto.getId() + "can not be found!!"));
        if (managerUpdateDto.getAddress() != null) {
            manager.setAddress(managerUpdateDto.getAddress());
        }
        if (managerUpdateDto.getName() != null) {
            manager.setName(managerUpdateDto.getName());
        }
        if (managerUpdateDto.getCity() != null) {
            manager.setCity(managerUpdateDto.getCity());
        }
        if (managerUpdateDto.getEmail() != null) {
            manager.setEmail(managerUpdateDto.getEmail());
            manager.getUser().setUsername(manager.getEmail());
        }
        if (managerUpdateDto.getEndDate() != null) {
            manager.setEndDate(managerUpdateDto.getEndDate());
        }
        if (managerUpdateDto.getPhoneNumber() != null) {
            manager.setPhoneNumber(managerUpdateDto.getPhoneNumber());
        }
       managerRepository.save(manager);

    }

    public ManagerReturnDto updateManager(ManagerUpdateDto managerUpdateDto) {

        Manager manager = managerRepository.findById(managerUpdateDto.getId())
                .orElseThrow(() -> new ManagerNotFoundException(
                        "This manager id " + managerUpdateDto.getId() + "can not be found!!"));
        if (managerUpdateDto.getAddress() != null) {
            manager.setAddress(managerUpdateDto.getAddress());
        }
        if (managerUpdateDto.getName() != null) {
            manager.setName(managerUpdateDto.getName());
        }
        if (managerUpdateDto.getCity() != null) {
            manager.setCity(managerUpdateDto.getCity());
        }
        if (managerUpdateDto.getEmail() != null) {
            manager.setEmail(managerUpdateDto.getEmail());
            manager.getUser().setUsername(manager.getEmail());
        }
        if (managerUpdateDto.getEndDate() != null) {
            manager.setEndDate(managerUpdateDto.getEndDate());
        }
        if (managerUpdateDto.getPhoneNumber() != null) {
            manager.setPhoneNumber(managerUpdateDto.getPhoneNumber());
        }
        manager = managerRepository.save(manager);

        return mapManager(manager);
    }

    public ManagerReturnDto assignEmployeeToManager(Long employeeId, Long managerId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        Manager manager = managerRepository.findById(managerId).orElseThrow();
        manager.getEmployees().add(employee);
        manager = managerRepository.save(manager);
        return mapManager(manager);
    }

    public String deleteManager(Long managerId) {

        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() ->
                        new ManagerNotFoundException("This manager id " + managerId + "was not found!"));

        managerRepository.deleteById(managerId);
        userRepository.deleteById(manager.getUser().getId());
        return "This manager has been deleted!";
    }

    public List<LeaveRequestReturnDto> getManagerPendingLeaveRequests(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();
        Manager manager = managerRepository.findByUser(user).orElseThrow();

        return manager.getLeaveRequestsToManage()
                .stream()
                .map(p -> leaveRequestService.mapLeaveRequestReturnDto(p))
                .collect(Collectors.toList());
    }

    public List<LeaveRequestReturnDto> myLeaveRequests(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();
        Manager manager = managerRepository.findByUser(user).orElseThrow();

        return manager.getLeaveRequests()
                .stream()
                .map(p -> leaveRequestService.mapLeaveRequestReturnDto(p))
                .collect(Collectors.toList());
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

        return mapManagerUpdate(manager);
    }

    public Integer getManagerRemainingDays(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();
        Manager manager = managerRepository.findByUser(user).orElseThrow();

        return manager.getAnnualLeaveDays();
    }
}
