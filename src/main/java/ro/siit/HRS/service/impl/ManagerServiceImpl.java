package ro.siit.HRS.service.impl;

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
import ro.siit.HRS.service.ManagerService;
import ro.siit.HRS.util.MapperUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private PasswordEncoder passwordEncoder;
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
    @Autowired
    private MapperUtil mapperUtil;

    public ManagerReturnDto findById(Long id) {

        return mapperUtil.mapManager(managerRepository.findById(id)
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
        manager.setAnnualLeaveDays(21);
        manager.setJobTitle("Head of department");
        manager = managerRepository.save(manager);
        return mapperUtil.mapManager(manager);
    }

    public void updateManagerFromEmployeeDto(EmployeeUpdateDto employeeUpdateDto) {

        Manager manager = managerRepository.findById(employeeUpdateDto.getId()).orElseThrow(()
                -> new ManagerNotFoundException("This manager id " + employeeUpdateDto.getId() + "can not be found!!"));
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

        Manager manager = managerRepository.findById(managerUpdateDto.getId()).orElseThrow(()
                -> new ManagerNotFoundException("This manager id " + managerUpdateDto.getId() + "can not be found!!"));
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

        Manager manager = managerRepository.findById(managerUpdateDto.getId()).orElseThrow(()
                -> new ManagerNotFoundException("This manager id " + managerUpdateDto.getId() + "can not be found!!"));
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

        return mapperUtil.mapManager(manager);
    }

    public ManagerReturnDto assignEmployeeToManager(Long employeeId, Long managerId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        Manager manager = managerRepository.findById(managerId).orElseThrow();
        manager.getEmployees().add(employee);
        manager = managerRepository.save(manager);
        return mapperUtil.mapManager(manager);
    }

    public String deleteManager(Long managerId) {

        Manager manager = managerRepository.findById(managerId).orElseThrow(()
                -> new ManagerNotFoundException("This manager id " + managerId + "was not found!"));

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
                .map(p -> mapperUtil.mapLeaveRequestReturnDto(p))
                .collect(Collectors.toList());
    }

    public List<LeaveRequestReturnDto> myLeaveRequests(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();
        Manager manager = managerRepository.findByUser(user).orElseThrow();

        return manager
                .getLeaveRequests()
                .stream()
                .map(p -> mapperUtil.mapLeaveRequestReturnDto(p))
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

        return mapperUtil.mapManagerUpdate(manager);
    }

    public Integer getManagerRemainingDays(String username) {

        User user = userRepository.findByUsername(username).orElseThrow();
        Manager manager = managerRepository.findByUser(user).orElseThrow();

        return manager.getAnnualLeaveDays();
    }
}
